package com.ort.parcial.c2.tp3.grupo10.data.initial

import android.content.Context
import com.ort.parcial.c2.tp3.grupo10.R
import com.ort.parcial.c2.tp3.grupo10.domain.model.Expense
import com.ort.parcial.c2.tp3.grupo10.domain.model.Category

object InitialExpensesData {
    fun getInitialCategories(context: Context): List<Category> {
        return listOf(
            // 3. USA context.getString() PARA OBTENER EL TEXTO
            Category(name = context.getString(R.string.category_food), iconResId = R.drawable.svg_food),
            Category(name = context.getString(R.string.category_transport), iconResId = R.drawable.svg_transport),
            Category(name = context.getString(R.string.category_medicine), iconResId = R.drawable.svg_medicine),
            Category(name = context.getString(R.string.category_groceries), iconResId = R.drawable.svg_groceries),
            Category(name = context.getString(R.string.category_rent), iconResId = R.drawable.svg_rent),
            Category(name = context.getString(R.string.category_gifts), iconResId = R.drawable.svg_gift),
            Category(name = context.getString(R.string.category_savings), iconResId = R.drawable.svg_savings),
            Category(name = context.getString(R.string.category_entertainment), iconResId = R.drawable.svg_entertainment),
            Category(name = context.getString(R.string.category_more), iconResId = R.drawable.svg_more)
        )
    }

    fun getInitialSavings(context: Context): List<Category> {
        return listOf(
            Category(name = context.getString(R.string.category_travel), iconResId = R.drawable.svg_avion),
            Category(name = context.getString(R.string.category_new_house), iconResId = R.drawable.svg_casallave),
            Category(name = context.getString(R.string.category_car), iconResId = R.drawable.svg_auto),
            Category(name = context.getString(R.string.category_wedding), iconResId = R.drawable.svg_anillos)
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
            ),
            // Savings - Travel
            Expense(
                id = "11",
                title = "Flight Ticket",
                amount = 450.00,
                date = "2024-04-20",
                time = "10:00",
                category = "Travel",
                iconResId = R.drawable.svg_avion
            ),
            Expense(
                id = "12",
                title = "Hotel Reservation",
                amount = 320.50,
                date = "2024-04-15",
                time = "14:30",
                category = "Travel",
                iconResId = R.drawable.svg_avion
            ),
            Expense(
                id = "13",
                title = "Travel Insurance",
                amount = 85.00,
                date = "2024-04-10",
                time = "09:15",
                category = "Travel",
                iconResId = R.drawable.svg_avion
            ),
            // Savings - New house
            Expense(
                id = "14",
                title = "Down Payment",
                amount = 15000.00,
                date = "2024-04-25",
                time = "11:00",
                category = "New house",
                iconResId = R.drawable.svg_casallave
            ),
            Expense(
                id = "15",
                title = "Inspection Fee",
                amount = 500.00,
                date = "2024-04-18",
                time = "16:00",
                category = "New house",
                iconResId = R.drawable.svg_casallave
            ),
            Expense(
                id = "16",
                title = "Legal Fees",
                amount = 1200.00,
                date = "2024-04-12",
                time = "13:45",
                category = "New house",
                iconResId = R.drawable.svg_casallave
            ),
            // Savings - Car
            Expense(
                id = "17",
                title = "Car Down Payment",
                amount = 5000.00,
                date = "2024-04-28",
                time = "10:30",
                category = "Car",
                iconResId = R.drawable.svg_auto
            ),
            Expense(
                id = "18",
                title = "Car Insurance",
                amount = 650.00,
                date = "2024-04-22",
                time = "15:20",
                category = "Car",
                iconResId = R.drawable.svg_auto
            ),
            Expense(
                id = "19",
                title = "Registration Fee",
                amount = 250.00,
                date = "2024-04-20",
                time = "09:00",
                category = "Car",
                iconResId = R.drawable.svg_auto
            ),
            // Savings - Wedding
            Expense(
                id = "20",
                title = "Venue Deposit",
                amount = 3000.00,
                date = "2024-04-30",
                time = "12:00",
                category = "Wedding",
                iconResId = R.drawable.svg_anillos
            ),
            Expense(
                id = "21",
                title = "Catering Service",
                amount = 2500.00,
                date = "2024-04-25",
                time = "14:00",
                category = "Wedding",
                iconResId = R.drawable.svg_anillos
            ),
            Expense(
                id = "22",
                title = "Photographer",
                amount = 1200.00,
                date = "2024-04-20",
                time = "11:30",
                category = "Wedding",
                iconResId = R.drawable.svg_anillos
            )
        )
    }
}