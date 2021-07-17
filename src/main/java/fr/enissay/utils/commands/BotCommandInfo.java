package fr.enissay.utils.commands;

import net.dv8tion.jda.api.Permission;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface BotCommandInfo {

    /**
     *
     * Get the module name
     *
     * @return false
     */
    String getModuleName() default "none-";

    /**
     *
     * Check the ID of the {@link BotCommand}
     *
     * @return 0
     */
    int ID();

    /**
     *
     * Get the prefix
     *
     * @return 0
     */
    String getPrefix() default "!";

    /**
     *
     * Get the permission
     *
     * @return 0
     */
    Permission getPermission() default Permission.UNKNOWN;

}