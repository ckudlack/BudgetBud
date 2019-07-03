package com.cdk.budgetbud

import android.os.Build
import com.cdk.budgetbud.viewmodel.BudgetItem
import com.cdk.budgetbud.viewmodel.BudgetViewItem
import com.cdk.budgetbud.viewmodel.BudgetViewType
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object BudgetItemMapper {

    fun toBudgetViewItemList(items: List<BudgetItem>): List<BudgetViewItem> {
        val viewItems: MutableList<BudgetViewItem> = mutableListOf()

        items.forEachIndexed { index, budgetItem ->
            if (index > 0) {
                val previousItem = items[index - 1]

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val previousInstant = Instant.ofEpochMilli(previousItem.time)

                    val thisInstant = Instant.ofEpochMilli(budgetItem.time)

                    val thisDate = thisInstant.atZone(ZoneId.systemDefault())
                    if (previousInstant.atZone(ZoneId.systemDefault()).dayOfYear != thisDate.dayOfYear) {
                        val f = DateTimeFormatter.ofPattern("MM/dd/uuuu")
                        val output = thisDate.format(f)
                        viewItems.add(BudgetViewItem(output, BudgetViewType.HEADER))
                        viewItems.add(BudgetViewItem("${budgetItem.name} : ${budgetItem.cost}", BudgetViewType.ITEM))
                    } else {
                        viewItems.add(BudgetViewItem("${budgetItem.name} : ${budgetItem.cost}", BudgetViewType.ITEM))
                    }
                } else {
                    TODO("VERSION.SDK_INT < O")
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    val thisInstant = Instant.ofEpochMilli(budgetItem.time)
                    val thisDate = thisInstant.atZone(ZoneId.systemDefault())

                    val f = DateTimeFormatter.ofPattern("MM/dd/uuuu")
                    val output = thisDate.format(f)
                    viewItems.add(BudgetViewItem(output, BudgetViewType.HEADER))
                    viewItems.add(BudgetViewItem("${budgetItem.name} : ${budgetItem.cost}", BudgetViewType.ITEM))
                } else {
                    TODO("VERSION.SDK_INT < O")
                }
            }
        }
        return viewItems
    }
}