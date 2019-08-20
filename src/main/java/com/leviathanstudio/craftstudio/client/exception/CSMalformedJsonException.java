package com.leviathanstudio.craftstudio.client.exception;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Exception raised when there is an error during json reading caused by a
 * malformed json.
 *
 * @author Timmypote
 * @since 0.3.0
 */
@OnlyIn(Dist.CLIENT)
public class CSMalformedJsonException extends Exception {
    private static final long serialVersionUID = -3495512445212088386L;

    /**
     * Create a exception for a missing field.
     *
     * @param field The name of the field.
     * @param type  The type of the field.
     * @param ress  The resource that is malformed.
     */
    public CSMalformedJsonException(String field, String type, String ress) {
        super("Missing field " + field + " of type " + type + " in " + ress);
    }

    /**
     * Create a exception for a malformed field.
     *
     * @param element Element that is malformed.
     * @param ress    The resource that is malformed.
     */
    public CSMalformedJsonException(String element, String ress) {
        super("Malformation of " + element + " in " + ress);
    }
}
