package com.makentoshe.ascicd.copy

import com.makentoshe.ascicd.Action

class CopyAction : Action<CopyCommand> {
    override fun execute(arg: CopyCommand) {
        arg.target.copyRecursively(arg.destination, true)
    }
}
