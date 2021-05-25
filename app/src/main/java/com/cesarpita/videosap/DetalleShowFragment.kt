package com.cesarpita.videosap

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.toSpanned
import com.bumptech.glide.Glide
import com.cesarpita.videosap.DetalleShowActivity.Companion.SHOW
import com.cesarpita.videosap.modelos.Show
import kotlinx.android.synthetic.main.fragment_detalle_show.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [DetalleShowFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetalleShowFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Show? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(SHOW) as Show

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.titulo_det.text = param1?.name
        if (param1?.summary!=null) {
            view.desc_det.text = Html.fromHtml(param1?.summary)
        }
        val url = param1?.image?.original
        //!! si no encuentra activity no continua con el resto
        Glide.with(activity!!).load(url).placeholder(R.drawable.ic_baseline_image_grey).into(view.portada_det)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment DetalleShowFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Show) =
            DetalleShowFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(SHOW, param1)
                }
            }
    }
}