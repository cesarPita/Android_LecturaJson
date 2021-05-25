package com.cesarpita.videosap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cesarpita.videosap.DetalleShowActivity.Companion.SHOW
import com.cesarpita.videosap.modelos.Show
import kotlinx.android.synthetic.main.fragment_informacion_tecnica.view.*
import kotlinx.android.synthetic.main.show_item.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [InformacionTecnicaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InformacionTecnicaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Show? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(SHOW) as Show?

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_informacion_tecnica, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.titulo_peli_f2.text = param1?.name
        view.runtime_peli_f2.text = param1?.runtime.toString()
        view.genero_peli_f2.text = param1?.genres.toString()
        view.premiere_peli_f2.text = param1?.premiered
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InformacionTecnicaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Show) =
            InformacionTecnicaFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(SHOW, param1)
                }
            }
    }
}