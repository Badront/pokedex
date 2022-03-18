package com.badront.pokedex.core.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.badront.pokedex.core.ext.kotlin.collections.filterIfIn
import com.badront.pokedex.core.ext.kotlin.collections.filterIfNotIn

abstract class SimpleListDao<T : Any> {

    /**
     * простая вставка новых элементов.
     * если элементы уже были в базе, новые их заменят.
     * @param entities - список новых элементов
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entities: List<T>)

    /**
     * простая вставка новых элементов.
     * если элементы уже были в базе, новые их заменят.
     * @param entities - массив новых элементов
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(vararg entities: T)

    /**
     * обновление списка элементов.
     * @param entities - список новых элементов к обновлению.
     */
    @Update
    abstract suspend fun update(entities: List<T>)

    /**
     * обновление массива элементов.
     * @param entities - массив новых элементов к обновлению.
     */
    @Update
    abstract suspend fun update(vararg entities: T)

    /**
     * удаление списка элементов.
     * @param entities - список элементов на удаление.
     */
    @Delete
    abstract suspend fun delete(entities: List<T>)

    /**
     * удаление массива элементов.
     * @param entities - массив элементов на удаление.
     */
    @Delete
    abstract suspend fun delete(vararg entities: T)

    /**
     * Производит обновление, вставку новых элементов.
     * При этом элементы, которые были раньше, но в новом списке отсутствуют - удаляются,
     * элементы, которые были раньше, но есть и в новом списке - обновляются,
     * элементы, которых не было раньше, - добавляются.
     * Для обновления, удаления и добавления элементов используются стандартные функции Dao.
     * @param newItems - список новых элементов, которые нужно положить в базу
     * @param oldItems - список старых элементов, которые сейчас есть в базе
     * @param sameItemPredicate - предикат для определения, что 2 элемента (из старой и новой выборки) одинаковые.
     */
    protected suspend inline fun insertOrUpdate(
        newItems: List<T>,
        oldItems: List<T>,
        crossinline sameItemPredicate: (newItem: T, oldItem: T) -> Boolean
    ) {
        insertOrUpdate(
            newItems = newItems,
            oldItems = oldItems,
            onDelete = {
                delete(it)
            },
            onUpdate = {
                update(it)
            },
            onInsert = {
                insert(it)
            },
            sameItemPredicate = sameItemPredicate
        )
    }

    /**
     * Производит обновление, вставку новых элементов.
     * При этом элементы, которые были раньше, но в новом списке отсутствуют - удаляются,
     * элементы, которые были раньше, но есть и в новом списке - обновляются,
     * элементы, которых не было раньше, - добавляются.
     * @param newItems - список новых элементов, которые нужно положить в базу
     * @param oldItems - список старых элементов, которые сейчас есть в базе
     * @param onDelete - функция, вызываемая для удаления элементов
     * @param onUpdate - функция, вызываемая для обновления элементов
     * @param onInsert - функция, вызываемая для вставки элементов
     * @param sameItemPredicate - предикат для определения, что 2 элемента (из старой и новой выборки) одинаковые.
     */
    @Suppress("LongParameterList")
    protected suspend inline fun <R : Any> insertOrUpdate(
        newItems: List<R>,
        oldItems: List<R>,
        crossinline onDelete: suspend (entities: List<R>) -> Unit,
        crossinline onUpdate: suspend (entities: List<R>) -> Unit,
        crossinline onInsert: suspend (entities: List<R>) -> Unit,
        crossinline sameItemPredicate: (newItem: R, oldItem: R) -> Boolean
    ) {
        val toDelete = oldItems
            .filterIfNotIn(newItems) { oldItem, newItem ->
                sameItemPredicate(newItem, oldItem)
            }
        onDelete(toDelete)
        val toUpdate = newItems
            .filterIfIn(oldItems) { newItem, oldItem ->
                sameItemPredicate(newItem, oldItem)
            }
        onUpdate(toUpdate)
        val toInsert = newItems
            .filterIfNotIn(oldItems) { newItem, oldItem ->
                sameItemPredicate(newItem, oldItem)
            }
        onInsert(toInsert)
    }
}