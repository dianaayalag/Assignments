package com.emedinaa.kotlinapp.model

object Data {

    fun categories() = listOf(
        Category(1, "Entradas", "Entradas"),
        Category(2, "Segundos", "Segundos"),
        Category(3, "Sopas", "Sopas"),
        Category(4, "Postres", "Postres"),
        Category(5, "Vinos", "Vinos")
    )

    fun dishes() = listOf(
        Dish(1, 2, "Lomo Saltado", "", "15.90", "Incluye entrada y refresco"),
        Dish(2, 2, "Tallarines Rojos", "", "12.50", "Incluye entrada y refresco"),
        Dish(3, 2, "Arroz con Pato", "", "15.90", "Incluye entrada y refresco"),
        Dish(4, 2, "Carapulcra", "", "15.90", "Incluye entrada y refresco"),
        Dish(5, 2, "Ají de Gallina", "", "15.90", "Incluye entrada y refresco"),
        Dish(6, 2, "Pollo a la Olla", "", "15.90", "Incluye entrada y refresco"),
        Dish(7, 3, "Caldo Gallina", "", "15.90", "Incluye entrada y refresco"),
        Dish(8, 3, "Caldo Menestron", "", "15.90", "Incluye entrada y refresco"),
        Dish(9, 3, "Sopa criolla", "", "15.90", "Incluye entrada y refresco"),
        Dish(10, 3, "Sopa a la minuta", "", "15.90", "Incluye entrada y refresco"),
        Dish(11, 3, "Crema de Zapallo", "15.90", "15.90", "Incluye entrada y refresco"),
        Dish(12, 3, "Parihuela", "", "15.90", "Incluye entrada y refresco"),
        Dish(13, 3, "Chilcano", "", "15.90", "Incluye entrada y refresco"),
        Dish(14, 3, "Aguadito", "", "15.90", "Incluye entrada y refresco"),
        Dish(15, 4, "Torta de chocolate", "", "15.90", "Incluye entrada y refresco"),
        Dish(16, 4, "Tres Leches", "", "15.90", "Incluye entrada y refresco"),
        Dish(17, 4, "Crema Volteada", "", "15.90", "Incluye entrada y refresco"),
        Dish(18, 4, "Torta Helada", "", "15.90", "Incluye entrada y refresco"),
        Dish(19, 4, "Picarones", "", "15.90", "Incluye entrada y refresco"),
        Dish(20, 4, "Pie de manzana", "", "15.90", "Incluye entrada y refresco"),
        Dish(21, 4, "Pie de limón", "", ".15.90", "Incluye entrada y refresco"),
        Dish(22, 4, "Supiro a la limeña", "15.90", "", "Incluye entrada y refresco"),
        Dish(23, 1, "Causa rellena", "", "15.90", "Incluye entrada y refresco"),
        Dish(24, 1, "Ocupa", "", "15.90", "Incluye entrada y refresco"),
        Dish(25, 1, "Ensalada Rusa", "", "15.90", "Incluye entrada y refresco"),
        Dish(26, 1, "Tequeños", "", "15.90", "Incluye entrada y refresco"),
        Dish(27, 1, "Palta Rellena", "", "15.90", "Incluye entrada y refresco"),
        Dish(28, 1, "Tamales", "", "15.90", "Incluye entrada y refresco"),
    )

    fun dishesByCategory(category: Int) = dishes().filter {
        it.category == category
    }

    fun orders() = listOf(OrderViewHeader(),CartItem("100",2,"1","Arroz con Pollo", 15.90),
        CartItem("100",1,"1","Tallarines Rojos", 15.90),   CartItem("100",3,"1","Caldo de Gallina", 15.90),
        OrderViewFooter(31.80))
}