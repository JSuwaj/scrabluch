package wordPointCalculator

sealed trait PointBonus

case object DoubleLetter extends PointBonus
case object TripleLetter extends PointBonus
case object DoubleWord extends PointBonus
case object TripleWord extends PointBonus
case object NoBonus extends PointBonus


