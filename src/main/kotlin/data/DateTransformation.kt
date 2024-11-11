package data

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation


class DateTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return dateFilter(text)
    }
}

private fun dateFilter(text: AnnotatedString): TransformedText {

    val trimmed = if (text.text.length >= 8) text.text.substring(0..7) else text.text
    var out = ""
    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i == 3 || i == 5) out += "/"
    }

    val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 3) return offset
            if (offset <= 5) return offset + 1
            if (offset <= 8) return offset + 2
            return 10
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 4) return offset
            if (offset <= 7) return offset - 1
            if (offset <= 10) return offset - 2
            return 8
        }
    }

    return TransformedText(AnnotatedString(out), numberOffsetTranslator)
}

fun formatDate(date: String): String {
    val newDate = if (date.length <= 8) {
        date.padEnd(10, '_')
    } else {
        date
    }

    val year = newDate.substring(0, 4) // первые 4 символа - год
    val month = newDate.substring(4, 6) // следующие 2 символа - месяц
    val day = newDate.substring(6, 8) // последние 2 символа - день

    return "$year/$month/$day"
}