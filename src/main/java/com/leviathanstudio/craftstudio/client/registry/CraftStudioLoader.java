package com.leviathanstudio.craftstudio.client.registry;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to indicate that the method it is applied on should be called
 * during the loading of CraftStudio assets. Must be applied on a 'static'
 * method with no arguments.
 *
 * @author Timmypote
 * @since 1.0.0
 */
@OnlyIn(Dist.CLIENT)
@Retention(RUNTIME)
@Target(METHOD)
public @interface CraftStudioLoader {
}
