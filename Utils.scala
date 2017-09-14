trait Utils {

  type HgDict     = Map[Char, Array[Char]]
  type Homoglyphs = Array[Char]

  def binToNat(bs: String): BigInt = BigInt(bs, 2)

  def prePadWith[T](e: T)(s: String, len: BigInt) =
    s.reverse.padTo(len.intValue, e).reverse.mkString

  // ensure secretBits are divisible by alphabetBitsSize
  def postPadSecretBits(sb: String, sAlphabetBitsSize: Int): String =
    if (BigInt(sb) % sAlphabetBitsSize == 0) sb
    else sb + prePadWith(0)("0", sAlphabetBitsSize - (BigInt(sb) % sAlphabetBitsSize))

}
