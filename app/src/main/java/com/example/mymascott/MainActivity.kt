package com.example.mymascott

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mymascott.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.tvGestureDetails.text = getString(R.string.start_text)

        startAnimateMyMascot()

        handleGestures()

        handlingInactivity()


    }

    private fun handlingInactivity() {
        handler = Looper.myLooper()?.let { Handler(it) }!!
        runnable = Runnable {
            binding.ivMascot.animate().apply {
                binding.ivMascot.startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.blink))
                binding.tvGestureDetails.text = getString(R.string.prompt_an_event)
            }
        }
        startHandler()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun handleGestures() {

        binding.ivMascot.setOnTouchListener(object : OnSwipeTouchListener(this@MainActivity) {

            override fun onSwipeBottom() {
                binding.ivMascot.animate().apply {
                    duration = 400
                    rotationXBy(360f)
                }.start()
                binding.tvGestureDetails.text = getString(R.string.swipe_bottom)
                super.onSwipeBottom()
            }

            override fun onSwipeLeft() {

                binding.ivMascot.animate().apply {
                    duration = 400
                    rotationYBy(-360f)
                }.start()
                binding.tvGestureDetails.text = getString(R.string.swipe_left)
                super.onSwipeLeft()
            }

            override fun onSwipeRight() {

                binding.ivMascot.animate().apply {
                    duration = 400
                    rotationYBy(360f)
                }.start()
                binding.tvGestureDetails.text = getString(R.string.swipe_right)
                super.onSwipeRight()
            }

            override fun onSwipeTop() {
                binding.ivMascot.animate().apply {
                    duration = 400
                    rotationXBy(-360f)
                }.start()
                binding.tvGestureDetails.text = getString(R.string.swipe_top)
                super.onSwipeTop()
            }

            override fun onDoubleTapPressed() {
                binding.ivMascot.startAnimation(AnimationUtils.loadAnimation(this@MainActivity,
                    R.anim.rotate))
                binding.tvGestureDetails.text = getString(R.string.double_tap)
                super.onDoubleTapPressed()
            }

            override fun onSingleTapConfirm() {
                binding.ivMascot.startAnimation(AnimationUtils.loadAnimation(this@MainActivity,
                    R.anim.shake))
                binding.tvGestureDetails.text = getString(R.string.single_tap_confirmed)
                super.onSingleTapConfirm()
            }

            override fun onLongPressed() {
                binding.ivMascot.startAnimation(AnimationUtils.loadAnimation(this@MainActivity,
                    R.anim.long_press))
                binding.tvGestureDetails.text = getString(R.string.long_pressed)
                super.onLongPressed()
            }
        })


    }


    private fun startAnimateMyMascot() {
        binding.ivMascot.animate().apply {
            duration = 1000
            alpha(0.5F)
            scaleYBy(.2f)
            scaleXBy(.2f)
            rotationXBy(360f)
            translationYBy(200f)
        }.withEndAction {
            binding.ivMascot.animate().apply {
                duration = 1000
                alpha(1F)
                scaleYBy(-0.2f)
                scaleXBy(-0.2f)
                rotationYBy(360f)
                translationYBy(-200f)
            }.start()
        }
    }


    override fun onUserInteraction() {
        super.onUserInteraction()
        stopHandler()
        startHandler()
    }


    private fun stopHandler() {
        handler.removeCallbacks(runnable)
    }


    private fun startHandler() {
        handler.postDelayed(runnable, 10000.toLong())
    }

    override fun onResume() {
        super.onResume()
       startHandler()
    }

    override fun onStop() {
        super.onStop()
        stopHandler()
    }


}