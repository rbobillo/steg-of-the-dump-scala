class Encoder(homoglyphsDict: Map[Char, Array[Char]], secretAlphabet: String) extends Utils {

  private def secretToBinaryString(secret: String, sAlpha: String, sAlphaBitsSize: Int): String = {
    val secretBits: String = secret.foldLeft(""){ (sb, c) =>
      sb + prePadWith(0)(sAlpha.indexOf(c).toBinaryString, sAlphaBitsSize)
    }
    postPadSecretBits(secretBits, sAlphaBitsSize)
  }

  private def replaceChar(c: Char, hg: Homoglyphs, sbits: String): (String,Char) = {
    val hgBitsSize     = (hg.size + 1).toBinaryString.size - 1
    val chunkToEncode  = sbits take hgBitsSize
    val nextSecretBits = sbits drop hgBitsSize
    val n              = binToNat(chunkToEncode).intValue
    val nextChar       = hg.lift(n-1).getOrElse(c)

    nextSecretBits -> nextChar
  }

  private def cipher(msg: String, sbits: String, dict: HgDict, result: String = ""): String =
   dict.get(msg.head) match {
      case None             => cipher(msg drop 1, sbits, dict, result + msg.head)
      case Some(homoglyphs) => (sbits, msg.head) match {
        case ("", ' ') => result
        case ("", _)   => cipher(msg drop 1, sbits, dict, result + msg.head)
        case (_, c)    => val (nbits, nchar) = replaceChar(c, homoglyphs, sbits)
                          cipher(msg drop 1, nbits, dict, result + nchar)
      }
    }

  def encode(message: String, secret: String): String = {
    val secretAlphabetBitsSize: Int = secretAlphabet.size.toBinaryString.size
    val secretBits: String = secretToBinaryString(secret, secretAlphabet, secretAlphabetBitsSize)

    cipher(message, secretBits, homoglyphsDict)
  }

}
