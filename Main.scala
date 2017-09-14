object Main {

  val LOREM = """Lorem ipsum nunc risus lacinia nisi feugiat etiam ad id pellentesque sit suscipit sociosqu nullam, turpis euismod velit id aliquam luctus praesent congue luctus mauris hendrerit posuere suscipit fringilla aptent egestas vivamus scelerisque id nulla inceptos facilisis, ac vehicula bibendum tortor aliquet tristique curabitur elit facilisis, quisque primis urna interdum nisl nulla class litora convallis ut tellus."""

  def encodeMessage(message: String, secret: String): String = {
    val encoder = Encoder(Alphabet.homoglyphsDict, Alphabet.secretAlphabet)

    encoder.encode(message, secret+" ") // adding a space after the secret to assure its integrity after encoding
  }

  def decodeMessage(message: String): String = {
    val decoder = Decoder(Alphabet.homoglyphsDict, Alphabet.secretAlphabet)

    decoder.decode(message)
  }

  def main(av: Array[String]): Unit =
    av.toList match {
      case str :: sec :: Nil => println(encodeMessage(str, sec))
      case        sec :: Nil => println(encodeMessage(LOREM, sec))
      case _                 => println("usage: scala hide.jar <string> <secret>")
    }

}
