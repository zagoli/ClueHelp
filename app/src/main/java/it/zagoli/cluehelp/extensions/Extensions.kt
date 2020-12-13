package it.zagoli.cluehelp.extensions

import android.widget.TextView
import androidx.databinding.BindingAdapter
import it.zagoli.cluehelp.domain.GameObject
import it.zagoli.cluehelp.domain.GameObjectType

val List<GameObject>.suspects: List<GameObject>
    get() = this.filter { g -> g.type == GameObjectType.SUSPECT }

val List<GameObject>.weapons: List<GameObject>
    get() = this.filter { g -> g.type == GameObjectType.WEAPON }

val List<GameObject>.rooms: List<GameObject>
    get() = this.filter { g -> g.type == GameObjectType.ROOM }

@BindingAdapter("headerText")
fun TextView.setHeaderText(resId: Int) {
    this.text = context.getText(resId)
}