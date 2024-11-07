package board

sealed trait FieldState
case object Empty extends FieldState
case object Filled extends FieldState
