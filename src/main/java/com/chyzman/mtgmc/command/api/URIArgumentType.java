package com.chyzman.mtgmc.command.api;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.text.Text;

import java.net.URI;
import java.util.Collection;
import java.util.List;

public class URIArgumentType implements ArgumentType<URI> {
    private static final DynamicCommandExceptionType INVALID_URI_EXCEPTION = new DynamicCommandExceptionType(e -> Text.literal("Invalid URI: " + e));

    public static URIArgumentType uri() {
        return new URIArgumentType();
    }

    public static URI getURI(final CommandContext<?> context, final String name) {
        return context.getArgument(name, URI.class);
    }

    @Override
    public URI parse(StringReader reader) throws CommandSyntaxException {
        StringBuilder input = new StringBuilder();
        while (reader.canRead() && !String.valueOf(reader.peek()).isBlank()) {
            input.append(reader.read());
        }
        try {
            return URI.create(input.toString());
        } catch (IllegalArgumentException e) {
            throw INVALID_URI_EXCEPTION.create(input.toString());
        }
    }

    @Override
    public Collection<String> getExamples() {
        return List.of("https://example.com", "example.com");
    }
}
