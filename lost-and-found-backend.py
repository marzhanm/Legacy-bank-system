from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from datetime import date
import sqlite3
import os
from typing import List, Optional
import threading
import asyncio
import uvicorn

app = FastAPI()


# Database initialization
def init_db():
    if not os.path.exists('lost_and_found.db'):
        conn = sqlite3.connect('lost_and_found.db')
        c = conn.cursor()
        c.execute('''
            CREATE TABLE items (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                description TEXT,
                location TEXT,
                date_found DATE,
                contact_info TEXT,
                claimed BOOLEAN DEFAULT FALSE
            )
        ''')
        conn.commit()
        conn.close()


init_db()


class Item(BaseModel):
    name: str
    description: str
    location: str
    date_found: date
    contact_info: str
    claimed: Optional[bool] = False


class ItemResponse(Item):
    id: int


def dict_factory(cursor, row):
    d = {}
    for idx, col in enumerate(cursor.description):
        d[col[0]] = row[idx]
    return d


@app.get("/items", response_model=List[ItemResponse])
async def get_items():
    """
    View all items in the database.
    """
    conn = sqlite3.connect('lost_and_found.db')
    conn.row_factory = dict_factory
    c = conn.cursor()
    c.execute('SELECT * FROM items ORDER BY date_found DESC')
    items = c.fetchall()
    conn.close()
    return items


@app.post("/items", response_model=ItemResponse)
async def create_item(item: Item):
    """
    Add a new item to the lost and found database.
    """
    conn = sqlite3.connect('lost_and_found.db')
    c = conn.cursor()
    c.execute('''
        INSERT INTO items (name, description, location, date_found, contact_info)
        VALUES (?, ?, ?, ?, ?)
    ''', (item.name, item.description, item.location, item.date_found, item.contact_info))
    item_id = c.lastrowid
    conn.commit()
    conn.close()

    return {**item.dict(), "id": item_id}


@app.put("/items/{item_id}/claim")
async def claim_item(item_id: int):
    """
    Mark an item as claimed.
    """
    conn = sqlite3.connect('lost_and_found.db')
    c = conn.cursor()
    c.execute('SELECT * FROM items WHERE id = ?', (item_id,))
    item = c.fetchone()

    if not item:
        raise HTTPException(status_code=404, detail="Item not found")

    c.execute('UPDATE items SET claimed = TRUE WHERE id = ?', (item_id,))
    conn.commit()
    conn.close()
    return {"message": "Item claimed successfully"}


@app.delete("/items/{item_id}")
async def delete_item(item_id: int):
    """
    Delete an item from the lost and found database.
    """
    conn = sqlite3.connect('lost_and_found.db')
    c = conn.cursor()
    c.execute('SELECT * FROM items WHERE id = ?', (item_id,))
    item = c.fetchone()

    if not item:
        raise HTTPException(status_code=404, detail="Item not found")

    c.execute('DELETE FROM items WHERE id = ?', (item_id,))
    conn.commit()
    conn.close()
    return {"message": "Item deleted successfully"}


@app.get("/items/{item_id}", response_model=ItemResponse)
async def get_item(item_id: int):
    """
    View a specific item by its ID.
    """
    conn = sqlite3.connect('lost_and_found.db')
    conn.row_factory = dict_factory
    c = conn.cursor()
    c.execute('SELECT * FROM items WHERE id = ?', (item_id,))
    item = c.fetchone()
    conn.close()

    if not item:
        raise HTTPException(status_code=404, detail="Item not found")

    return item


def show_menu():
    while True:
        print("\nPlease choose an option by number:")
        print("1. To see all items")
        print("2. To add an item")
        print("3. To delete an item")
        print("4. To find an item by ID")
        print("5. Exit")

        choice = input("\nChoose the option: ")

        if choice == "1":
            print("\nFetching all items...\n")
            items = asyncio.run(get_items())  # Await the async function properly
            print(items)

        elif choice == "2":
            name = input("Enter item name: ")
            description = input("Enter description: ")
            location = input("Enter location: ")
            date_found = input("Enter date found (YYYY-MM-DD): ")
            contact_info = input("Enter contact info: ")
            item_data = Item(
                name=name,
                description=description,
                location=location,
                date_found=date_found,
                contact_info=contact_info
            )
            asyncio.run(create_item(item_data))  # Await the async function properly

        elif choice == "3":
            item_id = int(input("Enter item ID to delete: "))
            asyncio.run(delete_item(item_id))  # Await the async function properly

        elif choice == "4":
            item_id = int(input("Enter item ID to find: "))
            item = asyncio.run(get_item(item_id))  # Await the async function properly
            print(item)

        elif choice == "5":
            print("Exiting...")
            break

        else:
            print("Invalid option. Please try again.")


def run_uvicorn():
    uvicorn.run(app, host="localhost", port=8000, log_level="critical")


if __name__ == "__main__":
    # Run Uvicorn in a separate thread
    uvicorn_thread = threading.Thread(target=run_uvicorn)
    uvicorn_thread.start()

    # Show the menu in the main thread
    show_menu()
