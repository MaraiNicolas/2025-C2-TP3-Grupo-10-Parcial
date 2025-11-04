package com.ort.parcial.c2.tp3.grupo10.data.initial

import com.ort.parcial.c2.tp3.grupo10.R
import com.ort.parcial.c2.tp3.grupo10.domain.model.Expense
import com.ort.parcial.c2.tp3.grupo10.domain.model.Category

object InitialExpensesData {
    fun getInitialCategories(): List<Category> {
        return listOf(
            Category(name = "Food", iconResId = R.drawable.svg_food),
            Category(name = "Transport", iconResId = R.drawable.svg_transport),
            Category(name = "Medicine", iconResId = R.drawable.svg_medicine),
            Category(name = "Groceries", iconResId = R.drawable.svg_groceries),
            Category(name = "Rent", iconResId = R.drawable.svg_rent),
            Category(name = "Gifts", iconResId = R.drawable.svg_gift),
            Category(name = "Savings", iconResId = R.drawable.svg_savings),
            Category(name = "Entertainment", iconResId = R.drawable.svg_entertainment),
            Category(name = "More", iconResId = R.drawable.svg_more)
        )
    }
    
    fun getInitialExpenses(): List<Expense> {
        return listOf(
            Expense(
                id = "1",
                title = "Dinner",
                amount = 26.00,
                date = "2024-04-30",
                time = "18:27",
                category = "Food",
                iconResId = R.drawable.svg_food
            ),
            Expense(
                id = "2",
                title = "Delivery Pizza",
                amount = 18.35,
                date = "2024-04-24",
                time = "15:00",
                category = "Food",
                iconResId = R.drawable.svg_food
            ),
            Expense(
                id = "3",
                title = "Lunch",
                amount = 15.40,
                date = "2024-04-15",
                time = "12:30",
                category = "Food",
                iconResId = R.drawable.svg_food
            ),
            Expense(
                id = "4",
                title = "Brunch",
                amount = 12.13,
                date = "2024-04-08",
                time = "09:30",
                category = "Food",
                iconResId = R.drawable.svg_food
            ),
            Expense(
                id = "5",
                title = "Dinner",
                amount = 27.20,
                date = "2024-03-31",
                time = "20:50",
                category = "Food",
                iconResId = R.drawable.svg_food
            ),
            Expense(
                id = "6",
                title = "Fuel",
                amount = 3.53,
                date = "2024-03-30",
                time = "18:27",
                category = "Transport",
                iconResId = R.drawable.svg_transport
            ),
            Expense(
                id = "7",
                title = "Car Parts",
                amount = 26.75,
                date = "2024-03-30",
                time = "15:00",
                category = "Transport",
                iconResId = R.drawable.svg_transport
            ),
            Expense(
                id = "8",
                title = "New Tires",
                amount = 373.99,
                date = "2024-02-10",
                time = "12:47",
                category = "Transport",
                iconResId = R.drawable.svg_transport
            ),
            Expense(
                id = "9",
                title = "Car Wash",
                amount = 9.74,
                date = "2024-02-09",
                time = "09:30",
                category = "Transport",
                iconResId = R.drawable.svg_transport
            ),
            Expense(
                id = "10",
                title = "Public Transport",
                amount = 1.24,
                date = "2024-02-01",
                time = "07:50",
                category = "Transport",
                iconResId = R.drawable.svg_transport
            )
        )
    }
}