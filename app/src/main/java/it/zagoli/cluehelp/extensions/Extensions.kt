package it.zagoli.cluehelp.extensions

import com.google.android.material.card.MaterialCardView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import it.zagoli.cluehelp.R
import it.zagoli.cluehelp.domain.GameObject
import it.zagoli.cluehelp.domain.GameObjectType
import it.zagoli.cluehelp.domain.Player
import it.zagoli.cluehelp.domain.Question

/**
 * returns the suspects in a list of [GameObject]
 */
val List<GameObject>.suspects: List<GameObject>
    get() = this.filter { g -> g.type == GameObjectType.SUSPECT }

/**
 * returns the weapons in a list of [GameObject]
 */
val List<GameObject>.weapons: List<GameObject>
    get() = this.filter { g -> g.type == GameObjectType.WEAPON }

/**
 * returns the rooms in a list of [GameObject]
 */
val List<GameObject>.rooms: List<GameObject>
    get() = this.filter { g -> g.type == GameObjectType.ROOM }

/**
 * applies the specified callback to all players between [fromPlayer] and [untilPlayer] (excluded)
 * @param fromPlayer the starting point
 * @param untilPlayer the end point
 * @param callback the action you want to be performed on the players
 */
fun List<Player>.forFromPlayerUntilPlayer (fromPlayer: Player, untilPlayer: Player, callback: (Player) -> Unit) {
    val fromPlayerIndex = this.indexOf(fromPlayer)
    val untilPlayerIndex = this.indexOf(untilPlayer)
    val rightPlayersList: List<Player>
    // we build the new list because sadly we have no circular list here. Otherwise it would have been much cleaner
    // three different situations:
    when {
        // 1) untilPlayer is nobody and it's not in the list
        untilPlayerIndex == -1 -> {
            rightPlayersList = this.filter { g -> g!=fromPlayer }
        }
        // 2) fromPlayer before untilPlayer
        fromPlayerIndex < untilPlayerIndex -> {
            rightPlayersList = this.subList(fromPlayerIndex+1, untilPlayerIndex)
        }
        // 3) fromPlayer after untilPlayer
        fromPlayerIndex > untilPlayerIndex -> {
            rightPlayersList = this.subList(fromPlayerIndex+1, this.lastIndex+1) + this.subList(0, untilPlayerIndex)
        }
        // case for compiler to be happy
        else -> rightPlayersList = listOf()
    }
    // perform the action on the new list of players
    rightPlayersList.forEach {callback(it)}
}

/**
 * returns the number of valid objects in a list of [GameObjectWrapper] of a question
 */
val Question.validObjectsNumber: Int
    get() = this.gameObjects.count { gameObjectWrapper -> gameObjectWrapper.isValid() }

/**
 * returns the first valid object in a list of [GameObjectWrapper] of a question
 */
val Question.firstValidObject: GameObject
    get() = this.gameObjects.first { gameObjectWrapper -> gameObjectWrapper.isValid() }.gameObject

/**
 * applies the specified callback to every valid question
 * @param callback the action to be applied to every valid question
 */
fun List<Question>.forEachValidQuestion(callback: (Question) -> Unit) {
    this.filter { question -> question.isValid() }
        .forEach { question -> callback(question) }
}

/**
 * return true if the gameObject is contained in the list
 * @param gameObject the object to know if it's in the list
 */
fun List<GameObjectWrapper>.containsGameObject(gameObject: GameObject): Boolean {
    return this
        .map { gameObjectWrapper -> gameObjectWrapper.gameObject }
        .contains(gameObject)
}

/**
 * return the [GameObjectWrapper] associated with the given [gameObject] if present in the list.
 * @param gameObject the gameObject to search in the list
 */
fun List<GameObjectWrapper>.gowFromGameObject(gameObject: GameObject): GameObjectWrapper {
    return this.first { gameObjectWrapper -> gameObjectWrapper.gameObject == gameObject }
}

/**
 * this binder adapter sets the text of the header based on the resource id.
 */
@BindingAdapter("headerText")
fun TextView.setHeaderText(resId: Int) {
    this.text = context.getText(resId)
}

/**
 * adapter to color the text if the object was discovered by machine in mainGame
 */
@BindingAdapter("gameObjectColor")
fun TextView.gameObjectColor(gameObject: GameObject) {
    if (gameObject.isOwnerCalculated) {
        this.setTextColor(ContextCompat.getColor(this.context, R.color.primaryLightColor))
    }
}


/**
 * adapter to set up the textView corresponding to the suspect in all questions list
 */
@BindingAdapter("suspect")
fun TextView.suspectQuestionList(question: Question) {
    val suspectWrapper = question.gameObjects[0]
    this.text = suspectWrapper.gameObject.name
    if (!suspectWrapper.isValid() && question.isValid()) {
        this.setTextColor(ContextCompat.getColor(this.context, R.color.primaryTransparentColor))
    }
}

/**
 * adapter to set up the textView corresponding to the weapon in all questions list
 */
@BindingAdapter("weapon")
fun TextView.weaponQuestionList(question: Question) {
    val weaponWrapper = question.gameObjects[1]
    this.text = weaponWrapper.gameObject.name
    if (!weaponWrapper.isValid() && question.isValid()) {
        this.setTextColor(ContextCompat.getColor(this.context, R.color.primaryTransparentColor))
    }
}

/**
 * adapter to set up the textView corresponding to the room in all questions list
 */
@BindingAdapter("room")
fun TextView.roomQuestionList(question: Question) {
    val roomWrapper = question.gameObjects[2]
    this.text = roomWrapper.gameObject.name
    if (!roomWrapper.isValid() && question.isValid()) {
        this.setTextColor(ContextCompat.getColor(this.context, R.color.primaryTransparentColor))
    }
}

/**
 * adapter used to set the transparency for an invalid question
 */
@BindingAdapter("validQuestion")
fun MaterialCardView.validQuestion(question: Question) {
    if (!question.isValid()) {
        this.setCardForegroundColor(ContextCompat.getColorStateList(this.context, R.color.primaryTransparentColor))
    }
}