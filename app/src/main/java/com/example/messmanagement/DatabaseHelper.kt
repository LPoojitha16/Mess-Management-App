package com.example.messmanagement

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "messmanagement.db"
        private const val DATABASE_VERSION = 2 // Increment version for upgrade

        // Users table
        private const val TABLE_USERS = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_REG_NO = "reg_no"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"
        private const val COLUMN_ROLE = "role"

        // Orders table
        private const val TABLE_ORDERS = "orders"
        private const val COLUMN_ORDER_ID = "id"
        private const val COLUMN_ORDER_USERNAME = "username"
        private const val COLUMN_ITEM_NAME = "item_name"
        private const val COLUMN_QUANTITY = "quantity"
        private const val COLUMN_PRICE = "price"

        // Permissions table
        private const val TABLE_PERMISSIONS = "permissions"
        private const val COLUMN_PERMISSION_ID = "id"
        private const val COLUMN_PERMISSION_USERNAME = "username"
        private const val COLUMN_REQUEST = "request"
        private const val COLUMN_STATUS = "status"

        // Menu Items table
        private const val TABLE_MENU_ITEMS = "menu_items"
        private const val COLUMN_MENU_ITEM_ID = "id"
        private const val COLUMN_MENU_ITEM_NAME = "item_name"
        private const val COLUMN_MENU_ITEM_PRICE = "price"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create users table
        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_REG_NO TEXT,
                $COLUMN_NAME TEXT,
                $COLUMN_EMAIL TEXT,
                $COLUMN_USERNAME TEXT UNIQUE,
                $COLUMN_PASSWORD TEXT,
                $COLUMN_ROLE TEXT
            )
        """.trimIndent()
        db.execSQL(createUsersTable)

        // Create orders table
        val createOrdersTable = """
            CREATE TABLE $TABLE_ORDERS (
                $COLUMN_ORDER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_ORDER_USERNAME TEXT,
                $COLUMN_ITEM_NAME TEXT,
                $COLUMN_QUANTITY INTEGER,
                $COLUMN_PRICE INTEGER
            )
        """.trimIndent()
        db.execSQL(createOrdersTable)

        // Create permissions table
        val createPermissionsTable = """
            CREATE TABLE $TABLE_PERMISSIONS (
                $COLUMN_PERMISSION_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_PERMISSION_USERNAME TEXT,
                $COLUMN_REQUEST TEXT,
                $COLUMN_STATUS TEXT
            )
        """.trimIndent()
        db.execSQL(createPermissionsTable)

        // Create menu_items table
        val createMenuItemsTable = """
            CREATE TABLE $TABLE_MENU_ITEMS (
                $COLUMN_MENU_ITEM_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_MENU_ITEM_NAME TEXT UNIQUE,
                $COLUMN_MENU_ITEM_PRICE INTEGER
            )
        """.trimIndent()
        db.execSQL(createMenuItemsTable)

        // Insert default menu items
        db.execSQL("INSERT INTO $TABLE_MENU_ITEMS ($COLUMN_MENU_ITEM_NAME, $COLUMN_MENU_ITEM_PRICE) VALUES ('Extra Rice', 20)")
        db.execSQL("INSERT INTO $TABLE_MENU_ITEMS ($COLUMN_MENU_ITEM_NAME, $COLUMN_MENU_ITEM_PRICE) VALUES ('Extra Dal', 15)")
        db.execSQL("INSERT INTO $TABLE_MENU_ITEMS ($COLUMN_MENU_ITEM_NAME, $COLUMN_MENU_ITEM_PRICE) VALUES ('Extra Chapati', 10)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            // Create menu_items table if upgrading from version 1
            val createMenuItemsTable = """
                CREATE TABLE $TABLE_MENU_ITEMS (
                    $COLUMN_MENU_ITEM_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $COLUMN_MENU_ITEM_NAME TEXT UNIQUE,
                    $COLUMN_MENU_ITEM_PRICE INTEGER
                )
            """.trimIndent()
            db.execSQL(createMenuItemsTable)

            // Insert default menu items
            db.execSQL("INSERT INTO $TABLE_MENU_ITEMS ($COLUMN_MENU_ITEM_NAME, $COLUMN_MENU_ITEM_PRICE) VALUES ('Extra Rice', 20)")
            db.execSQL("INSERT INTO $TABLE_MENU_ITEMS ($COLUMN_MENU_ITEM_NAME, $COLUMN_MENU_ITEM_PRICE) VALUES ('Extra Dal', 15)")
            db.execSQL("INSERT INTO $TABLE_MENU_ITEMS ($COLUMN_MENU_ITEM_NAME, $COLUMN_MENU_ITEM_PRICE) VALUES ('Extra Chapati', 10)")
        }
    }
}