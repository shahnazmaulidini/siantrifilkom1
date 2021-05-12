package com.example.siantrifilkom1.ui.antrian

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.siantrifilkom1.R
import com.example.siantrifilkom1.databinding.FragmentDetailAntrianBinding
import com.example.siantrifilkom1.util.ModelAntrian
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*


class DetailAntrianFragment : Fragment() {
    lateinit var binding: FragmentDetailAntrianBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailAntrianBinding.inflate(layoutInflater, container, false)
        binding.IDDetailAntrianPopup.IDPopupCardView.visibility = View.GONE
        binding.IDDetailAntrianPopup.IDPopupLinear.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val date = SimpleDateFormat("ddMyyyy")
        val currentDateNow = date.format(Date())

        arguments?.let {data->
            FirebaseDatabase.getInstance().reference.child("SistemAntrian").child("Antrian").child(currentDateNow)
                .orderByChild("nomorAntrian").equalTo(data.getString("nomorAntrian")).addValueEventListener(
                    object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var key =""
                            snapshot.children.forEach {
                                val modeAntrian =
                                    it.getValue(ModelAntrian::class.java) as ModelAntrian
                                binding.IDDetailAtrianTxtNoAntrian.text = modeAntrian.nomorAntrian
                                binding.IDDetailAtrianTxtNama.text = modeAntrian.nama

                                key = it.key.toString()
                            }

                            binding.IDDetailAntrianBtnSelesai.setOnClickListener {
                                Toast.makeText(requireContext(), "key = "+key, Toast.LENGTH_SHORT).show()
                                val bundle = Bundle()
                                bundle.putString("nomorAntrian", data.getString("nomorAntrian"))
                                bundle.putString("keyAntrian", key)

                                findNavController().navigate(
                                    R.id.action_detailAntrianFragment_to_scannerQrFragment,
                                    bundle
                                )
                            }

                            binding.IDDetailAntrianPopup.IDDetailAntrianBtnYa.setOnClickListener {
                                binding.IDDetailAntrianPopup.IDPopupCardView.visibility = View.GONE
                                binding.IDDetailAntrianPopup.IDPopupLinear.visibility = View.GONE
                                FirebaseDatabase.getInstance().reference.child("SistemAntrian").child("Antrian").child(currentDateNow).child(key).child("status").setValue("Batal")

                                val bundle = Bundle()
                                bundle.putString("nomorAntrian", data.getString("nomorAntrian"))
                                bundle.putString("keyAntrian", key)


                                val navOption = NavOptions.Builder().setPopUpTo(R.id.antrianFragment,true).build()
                                findNavController().navigate(R.id.action_detailAntrianFragment_to_antrianFragment,null,navOption)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })

        }

        FirebaseDatabase.getInstance().reference.child("SistemAntrian").child("Antrian").child(currentDateNow)
            .orderByChild("status").equalTo("Aktif").addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {

                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        binding.IDDetailAntrianBtnBatal.setOnClickListener {
            binding.IDDetailAntrianPopup.IDPopupCardView.visibility = View.VISIBLE
            binding.IDDetailAntrianPopup.IDPopupLinear.visibility = View.VISIBLE
        }

        binding.IDDetailAntrianPopup.IDDetailAntrianBtnTidak.setOnClickListener {
            binding.IDDetailAntrianPopup.IDPopupCardView.visibility = View.GONE
            binding.IDDetailAntrianPopup.IDPopupLinear.visibility = View.GONE
        }


    }
}