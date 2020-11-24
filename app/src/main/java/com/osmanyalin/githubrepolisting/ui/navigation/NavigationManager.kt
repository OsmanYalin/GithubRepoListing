package com.osmanyalin.githubrepolisting.ui.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.osmanyalin.githubrepolisting.R
import javax.inject.Inject

class NavigationManager @Inject constructor(private val activity: FragmentActivity) {

    internal fun openFragment(fragment: Fragment, animation: Animation = Animation.HORIZONTAL, transaction: Transaction = Transaction.REPLACE,  addToBackStack: Boolean = true) {

        try {
            val fragmentTransaction = activity.supportFragmentManager.beginTransaction()

            when (animation) {
                Animation.HORIZONTAL -> fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                Animation.VERTICAL -> fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down, R.anim.slide_in_down, R.anim.slide_out_up)
            }

            when (transaction) {
                Transaction.ADD -> fragmentTransaction.add(
                    R.id.fragment_container,
                    fragment,
                    fragment.javaClass.name
                )

                Transaction.REPLACE -> fragmentTransaction.replace(
                    R.id.fragment_container,
                    fragment,
                    fragment.javaClass.name
                )
            }

            if (addToBackStack) {
                fragmentTransaction.addToBackStack(fragment.javaClass.name)
            }

            fragmentTransaction.commit()

        } catch (e:Exception) {
            e.printStackTrace()
        }
    }

    enum class Transaction {
        ADD,
        REPLACE
    }

    enum class Animation {
        HORIZONTAL,
        VERTICAL
    }
}