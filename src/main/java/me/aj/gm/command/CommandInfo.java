package me.aj.gm.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {

	String permission();

	String[] aliases();

	String description();

	boolean allowsConsole();

}