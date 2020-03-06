package imt3673.ass.groupexpenses.screens

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.marginLeft
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import imt3673.ass.groupexpenses.MainActivity
import imt3673.ass.groupexpenses.R
import imt3673.ass.groupexpenses.Utils.Expenses
import imt3673.ass.groupexpenses.Utils.SingleExpense
import imt3673.ass.groupexpenses.Utils.Transaction
import imt3673.ass.groupexpenses.Utils.convertAmountToString
import imt3673.ass.groupexpenses.databinding.FragmentMainBinding

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.btnSettlement.isEnabled = false

        var totalExpenses: Long = 0
        var averageExpense: Long = 0
        if (!(activity as MainActivity).expenses.allExpenses().isEmpty()) {
            totalExpenses = (activity as MainActivity).expenses.allExpenses().map { it.amount }.sum()
            averageExpense = totalExpenses / (activity as MainActivity).expenses.allExpenses().size
            (activity as MainActivity).expenses.allExpenses().forEach { renderTable(it) }
            binding.btnSettlement.isEnabled = true
        }

        binding.txtExpensesTotal.text = convertAmountToString(totalExpenses)
        binding.txtExpensesAvr.text = convertAmountToString(averageExpense)


        binding.btnAddData.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_mainFragment_to_addExpenseFragment)
        }

        binding.btnSettlement.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_mainFragment_to_settlementFragment)
        }

        return binding.root
    }

    private fun renderTable(expense: SingleExpense) {
        val row = TableRow(context)
        row.layoutParams = TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT)
        row.setPadding(10, 10, 10, 10)

        val person = TextView(context)
        person.text = expense.person
        person.gravity = Gravity.CENTER

        val amount = TextView(context)
        amount.text = convertAmountToString(expense.amount)
        amount.gravity = Gravity.CENTER

        val description = TextView(context)
        description.text = expense.description
        description.gravity = Gravity.CENTER

        row.addView(person)
        row.addView(amount)
        row.addView(description)

        binding.tblExpenses.addView(row)
    }

}
