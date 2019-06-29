package algorithm.string;

/**
 * https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html
 *
 * Regex syntax
 * ^    matches the beginning of the line
 * $    matches the end of the line
 * .    matches any char, except line break chars
 * [...]    matches any single char in brackets
 * [^...]   matches any single char not in brackets
 * re*      matches 0 or more occurrences of the preceding expression
 * re+      matches 1 or more occurrences ...
 * re?      matches 0 or 1 occurrence ...
 * re{n}    matches exactly n occurrences ...
 * re{n, m} matches at least n and at most m occurrences ...
 * re{n, }  matches at least n occurrences (no upper bound?)
 * a | b    matches either a or b
 * (re)     groups regular expressions and remembers the matched text
 * (?: re)  groups regular expressions without remembering the matched text
 * (?> re)  matches the independent pattern without backtracking
 * \w       matches the word characters
 * \W       matches the nonword characters
 * \s       matches whitespace. Equivalent to [\t\n\r\f]
 * \S       matches nonwhitespace
 * \d       matches digits. Equivalent to [0-9]
 * \D       matches nondigit
 * \A       matches the beginning of the string
 * \z       matches the end of the string
 * \Z       matches the end the string. If new line exists, it matches just before the new line
 * \G       matches the point where the last match finished
 * \n       back-reference to capture group number "n"
 * \b       matches the word boundaries when outside the brackets
 *          matches the backspace (0x08) when inside the brackets
 * \B       matches the nonword boundaries
 * \n, \t   matches new line, carriage returns, tabs...
 * \Q       escape all chars up \E
 * \E       ends quoting begun with \Q
 *
 * Pattern regex
 *
 * POSIX character class
 * \p{Lower}                lower-case alphabet char [a-z]
 * \p{Upper}                upper-case alphabet char [A-Z]
 * \p{ASCII}                all ASCII [\x00-\x7F]
 * \p{Alpha}                an alphabetic character [\p{Lower}\p{Upper}]
 * \p{Digit}                decimal digit [0-9]
 * \p{Alnum}                alphabet and numeric chars [\p{Alpha}\p{Digit}]
 * \p{Punct}                Punctuation: One of !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~
 * \p{Graph}                visible chars [\p{Alnum}\p{Punct}]
 * \p{Space}                white space chars [ \t\n\x0B\f\r]
 * \p{XDigit}               hexadecimal digit [0-9a-fA-F]
 * \p{Z} or \p{Separator}   any kind of whitespace or invisible separator
 *
 */
public class RegularExpressionDemo {
}
