package que.sera.sera.android2022.ui.main

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import que.sera.sera.android2022.R

class MainViewModel(
    @StringRes val greeting: Int = R.string.greeting
) : ViewModel()