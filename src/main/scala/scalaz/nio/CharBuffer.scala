package scalaz.nio

import scalaz.zio.{ IO, JustExceptions }

import java.nio.{ ByteOrder, CharBuffer => JCharBuffer }

private[nio] class CharBuffer(val charBuffer: JCharBuffer) extends Buffer[Char](charBuffer) {

  override val array: IO[Exception, Array[Char]] =
    IO.effect(charBuffer.array()).refineOrDie(JustExceptions)

  val order: IO[Nothing, ByteOrder] = IO.succeed(charBuffer.order())

  val slice: IO[Exception, CharBuffer] =
    IO.effect(charBuffer.slice()).map(new CharBuffer(_)).refineOrDie(JustExceptions)

  override val get: IO[Exception, Char] = IO.effect(charBuffer.get()).refineOrDie(JustExceptions)

  override def get(i: Int): IO[Exception, Char] =
    IO.effect(charBuffer.get(i)).refineOrDie(JustExceptions)

  override def put(element: Char): IO[Exception, CharBuffer] =
    IO.effect(charBuffer.put(element)).map(new CharBuffer(_)).refineOrDie(JustExceptions)

  override def put(index: Int, element: Char): IO[Exception, CharBuffer] =
    IO.effect(charBuffer.put(index, element)).map(new CharBuffer(_)).refineOrDie(JustExceptions)

  override val asReadOnlyBuffer: IO[Exception, CharBuffer] =
    IO.effect(charBuffer.asReadOnlyBuffer()).map(new CharBuffer(_)).refineOrDie(JustExceptions)
}
