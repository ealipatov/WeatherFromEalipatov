package by.ealipatov.kotlin.weatherfromealipatov.lesson06

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentThreadsBinding

class ThreadsFragment: Fragment() {

    private var _binding: FragmentThreadsBinding? = null
    private val binding: FragmentThreadsBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThreadsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("***","${Thread.currentThread()}")
        var counter =0

        binding.button.setOnClickListener {
            Thread{
                Thread.sleep(200L)
                Handler(Looper.getMainLooper()).post(Runnable {
                    binding.textView.text= hardWork()
                    binding.button.textSize =10f
                    binding.mainContainer.addView(TextView(requireContext()).apply {
                        text ="${counter++}"
                        textSize =20f
                    })
                })
            }.start()
        }

        val mThread =MyThread()
        mThread.start()
        binding.button2.setOnClickListener {
            mThread.handler?.let {
                it.post{
                    Log.d("***","${Thread.currentThread()} $counter")
                    hardWork()
                    Handler(Looper.getMainLooper()).post(Runnable {
                        binding.textView2.text=""
                        binding.button2.textSize =10f
                        binding.mainContainer.addView(TextView(requireContext()).apply {
                            text ="${counter++}"
                            textSize =20f
                        })
                    })
                }
            }
        }
    }

    class MyThread:Thread(){
        var handler: Handler?=null
        override fun run() {
            Looper.prepare()
            handler = Handler()
            Looper.loop()
        }
    }

    private fun hardWork():String{
        Thread.sleep(200L)
        return hardWork2()
    }

    private fun hardWork2():String{
        Thread.sleep(200L)
        return "Получилось"
    }
}