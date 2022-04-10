package com.emedinaa.kotlinapp.storage.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.emedinaa.kotlinapp.model.Category
import com.emedinaa.kotlinapp.model.Dish
import com.emedinaa.kotlinapp.utils.diskThread

/**
 * Created by Eduardo Medina on 7/5/21.
 */
@Database(entities = [CategoryDTO::class, DishDTO::class], version = 2, exportSchema = false)
abstract class RestaurantDataBase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun dishDao(): DishDao

    companion object {
        private const val DB_NAME = "RestaurantRoom.db"
        private var INSTANCE: RestaurantDataBase? = null

        private val categories = listOf(
            CategoryDTO("Entradas", "Entradas"),
            CategoryDTO( "Segundos", "Segundos"),
            CategoryDTO("Sopas", "Sopas"),
            CategoryDTO("Postres", "Postres"),
            CategoryDTO("Vinos", "Vinos")
        )

        private val  dishes = listOf(
            DishDTO(2, "Lomo Saltado", "", "15.90", "Incluye entrada y refresco"),
            DishDTO(2, "Tallarines Rojos", "", "12.50", "Incluye entrada y refresco"),
            DishDTO( 2, "Arroz con Pato", "", "15.90", "Incluye entrada y refresco"),
            DishDTO( 2, "Carapulcra", "", "15.90", "Incluye entrada y refresco"),
            DishDTO( 2, "Ají de Gallina", "", "15.90", "Incluye entrada y refresco"),
            DishDTO( 2, "Pollo a la Olla", "", "15.90", "Incluye entrada y refresco"),
            DishDTO(3, "Caldo Gallina", "", "15.90", "Incluye entrada y refresco"),
            DishDTO(3, "Caldo Menestron", "", "15.90", "Incluye entrada y refresco"),
            DishDTO( 3, "Sopa criolla", "", "15.90", "Incluye entrada y refresco"),
            DishDTO(3, "Sopa a la minuta", "", "15.90", "Incluye entrada y refresco"),
            DishDTO( 3, "Crema de Zapallo", "15.90", "15.90", "Incluye entrada y refresco"),
            DishDTO(3, "Parihuela", "", "15.90", "Incluye entrada y refresco"),
            DishDTO(3, "Chilcano", "", "15.90", "Incluye entrada y refresco"),
            DishDTO( 3, "Aguadito", "", "15.90", "Incluye entrada y refresco"),
            DishDTO(4, "Torta de chocolate", "", "15.90", "Incluye entrada y refresco"),
            DishDTO( 4, "Tres Leches", "", "15.90", "Incluye entrada y refresco"),
            DishDTO( 4, "Crema Volteada", "", "15.90", "Incluye entrada y refresco"),
            DishDTO(4, "Torta Helada", "", "15.90", "Incluye entrada y refresco"),
            DishDTO( 4, "Picarones", "", "15.90", "Incluye entrada y refresco"),
            DishDTO( 4, "Pie de manzana", "", "15.90", "Incluye entrada y refresco"),
            DishDTO( 4, "Pie de limón", "", ".15.90", "Incluye entrada y refresco"),
            DishDTO( 4, "Supiro a la limeña", "15.90", "", "Incluye entrada y refresco"),
            DishDTO( 1, "Causa rellena", "", "15.90", "Incluye entrada y refresco"),
            DishDTO(1, "Ocupa", "", "15.90", "Incluye entrada y refresco"),
            DishDTO( 1, "Ensalada Rusa", "", "15.90", "Incluye entrada y refresco"),
            DishDTO(1, "Tequeños", "", "15.90", "Incluye entrada y refresco"),
            DishDTO(1, "Palta Rellena", "", "15.90", "Incluye entrada y refresco"),
            DishDTO( 1, "Tamales", "", "15.90", "Incluye entrada y refresco"),
        )

        private val MIGRATION_1_2 = object :Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE tb_dish ADD COLUMN favorite INTEGER")
            }
        }

        fun getInstance(context: Context): RestaurantDataBase {
            if (INSTANCE == null) {
                synchronized(RestaurantDataBase::class.java) {
                    INSTANCE = Room.databaseBuilder(context, RestaurantDataBase::class.java, DB_NAME)
                        .addCallback(object:Callback(){
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                //prepopulate
                                diskThread {
                                    getInstance(context).categoryDao().insert(categories)
                                    getInstance(context).dishDao().insert(dishes)
                                }
                            }
                        })
                        .addMigrations(MIGRATION_1_2)
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE ?: throw Exception("DBNote !=null")
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}