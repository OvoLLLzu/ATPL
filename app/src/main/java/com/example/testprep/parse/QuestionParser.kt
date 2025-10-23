package com.example.testprep.parse

import com.example.testprep.data.entity.QuestionEntity

object QuestionParser {
    private val blockSeparator = Regex("\r?\n_{5,}\r?\n", RegexOption.MULTILINE)

    fun parse(raw: String): List<QuestionEntity> {
        val blocks = raw.split(blockSeparator)
            .map { it.trim() }
            .filter { it.isNotEmpty() }
        val result = mutableListOf<QuestionEntity>()
        for (block in blocks) {
            val lines = block.lines().map { it.trim() }.filter { it.isNotEmpty() }
            if (lines.size < 4) continue
            val questionLine = lines[0]
            val qText = questionLine.substringAfter(".", questionLine).trim()
            val opts = lines.drop(1).take(3)
            var correctIndex = -1
            val cleanOpts = opts.mapIndexed { idx, s ->
                val trimmed = s.trim()
                if (trimmed.startsWith("*")) {
                    if (correctIndex == -1) correctIndex = idx
                    trimmed.removePrefix("*").trim()
                } else trimmed
            }
            if (correctIndex == -1) continue
            result.add(
                QuestionEntity(
                    questionText = qText,
                    option1 = cleanOpts.getOrElse(0) { "" },
                    option2 = cleanOpts.getOrElse(1) { "" },
                    option3 = cleanOpts.getOrElse(2) { "" },
                    correctIndex = correctIndex
                )
            )
        }
        return result
    }
}
