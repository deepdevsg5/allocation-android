package com.example.allocation_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.allocation_app.model.Course
import com.example.allocation_app.model.Department
import com.example.allocation_app.model.Professor

abstract class AdapterDefault<T : Any>(var itens: MutableList<T>) : RecyclerView.Adapter<AdapterDefault<T>.ViewHolder>() {

    // copia a lista de courses que foi pega da API
    var filteredList = itens.toMutableList()

    // Adicione uma propriedade para armazenar o layout do item
    var itemLayout: Int = R.layout.itens_fields_names

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(itemLayout, parent, false)
        return ViewHolder(view)
    }

    //filtro da lista
    fun setFilteredList(query: String): Boolean {
        filteredList.clear()
        filteredList.addAll(itens.filter { filterCondition(it, query) })
        notifyDataSetChanged()
        return filteredList.isEmpty()
    }

    fun clearSearch() {
        filteredList.clear()
        filteredList.addAll(itens)
        notifyDataSetChanged()
    }

    // para definir a condição de filtro específica para cada tipo de item.
    abstract fun filterCondition(item: T, query: String): Boolean

    override fun getItemCount(): Int = filteredList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredList[position])
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(position)
        }
    }

    //funcao de clicar
    var onItemClick: ((Int) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: T) {

            val txtFirstChar = itemView.findViewById<TextView>(R.id.txt_first_char)
            val txtFieldId = itemView.findViewById<TextView>(R.id.txt_fieldId)
            val txtFieldName = itemView.findViewById<TextView>(R.id.txt_fieldName)
            val txtFieldCpf = itemView.findViewById<TextView>(R.id.txt_fieldCpf)
            val txtFieldDepartment = itemView.findViewById<TextView>(R.id.txt_fieldDepartment)
            val txtFieldCourse = itemView.findViewById<TextView>(R.id.txt_fieldCourse)
            val txtFieldProfessor = itemView.findViewById<TextView>(R.id.txt_fieldProfessor)
            val txtFieldHourStart = itemView.findViewById<TextView>(R.id.txt_fieldHourStart)
            val txtFieldHourEnd = itemView.findViewById<TextView>(R.id.txt_fieldHourEnd)




            when (item) {
                is Course -> {
                    // Trate o item como um objeto Course
                    val course = item as Course
                    // Configure os elementos de visualização conforme necessário

                    txtFieldId.text = course.id.toString()
                    txtFieldName.text = course.name
                    txtFirstChar.text = course.name.substring(0, 1)
                }

                is Department -> {
                    // Trate o item como um objeto Course
                    val department = item as Department

                    // Configure os elementos de visualização conforme necessário

                    txtFieldId.text = department.id.toString()
                    txtFieldName.text = department.name
                    txtFirstChar.text = department.name.substring(0, 1)
                }

                is Professor -> {
                    val professor = item as Professor

                    txtFieldId.text = professor.id.toString()
                    txtFieldName.text = professor.name
                    txtFirstChar.text = professor.name.substring(0,1)

                    txtFieldCpf.text = professor.cpf
                    txtFieldCpf.visibility = View.VISIBLE //ativar o campo

                    txtFieldDepartment.text = professor.departmentName
                    txtFieldDepartment.visibility = View.VISIBLE // ativar o campo

                }

            }
        }

    }

}



