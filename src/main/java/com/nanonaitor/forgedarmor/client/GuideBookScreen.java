package com.nanonaitor.forgedarmor.client;

import com.nanonaitor.forgedarmor.AttachmentInfo;
import com.nanonaitor.forgedarmor.AttachmentType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

/** Opens the Forged Armor guide in the vanilla book viewer; pages are rebuilt each time so config changes show. */
public final class GuideBookScreen {
    private static final String KEY = "guide.nanonaitor_forged_armor.";

    private GuideBookScreen() {}

    public static void open() {
        Minecraft.getInstance().setScreen(new BookViewScreen(new Access(pages())));
    }

    private static List<FormattedText> pages() {
        List<FormattedText> pages = new ArrayList<>();
        for (int i = 1; i <= 3; i++) pages.add(Component.translatable(KEY + "intro." + i));
        for (AttachmentType type : AttachmentType.values()) {
            if (!type.enabled()) continue;
            MutableComponent page = Component.empty();
            List<Component> lines = AttachmentInfo.lines(type);
            for (int i = 0; i < lines.size(); i++) {
                if (i > 0) page.append(i == 1 ? "\n" : "\n\n");
                page.append(lines.get(i));
            }
            pages.add(page);
        }
        return pages;
    }

    private record Access(List<FormattedText> pages) implements BookViewScreen.BookAccess {
        @Override public int getPageCount() { return pages.size(); }
        @Override public FormattedText getPageRaw(int index) { return pages.get(index); }
    }
}
