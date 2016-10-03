package net.starborne.server.transformer;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.starborne.server.core.StarborneHooks;
import net.starborne.server.core.transformer.TransformerClass;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class EntityTransformer extends TransformerClass {
    public EntityTransformer() {
        super(Entity.class);
    }

    @Override
    public boolean transform(ClassNode classNode, String transformedName) {
        MethodNode constructor = this.getMethod(classNode, "<init>", World.class, void.class);
        if (constructor != null) {
            Instruction instruction = this.instruction()
                    .var(ALOAD, 1)
                    .method(INVOKESTATIC, StarborneHooks.class, "getRealWorld", World.class, World.class)
                    .var(ASTORE, 1);
            this.insertAfter(constructor, node -> node.getOpcode() == INVOKESPECIAL && ((MethodInsnNode) node).owner.equals("java/lang/Object"), instruction.build(), false);
            return true;
        }
        return false;
    }
}