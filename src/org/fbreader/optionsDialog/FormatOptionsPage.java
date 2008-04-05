package org.fbreader.optionsDialog;

import org.zlibrary.core.dialogs.ZLDialogContent;
import org.zlibrary.core.optionEntries.ZLSimpleSpinOptionEntry;
import org.zlibrary.core.resources.ZLResource;
import org.zlibrary.text.view.ZLTextAlignmentOptionEntry;
import org.zlibrary.text.view.ZLTextLineSpaceOptionEntry;
import org.zlibrary.text.view.style.ZLTextBaseStyle;
import org.zlibrary.text.view.style.ZLTextStyleCollection;
import org.zlibrary.text.view.style.ZLTextStyleDecoration;
import org.zlibrary.text.view.style.ZLTextFullStyleDecoration;

import static org.fbreader.bookmodel.FBTextKind.*;

public class FormatOptionsPage extends OptionsPage {
	private static final String KEY_STYLE = "style";
	private static final String KEY_BASE = "Base";

	private static final String KEY_DUMMY = "";
	private static final String KEY_LINESPACING = "lineSpacing";
	private static final String KEY_FIRSTLINEINDENT = "firstLineIndent";
	private static final String KEY_ALIGNMENT = "alignment";
	private static final String KEY_SPACEBEFORE = "spaceBefore";
	private static final String KEY_SPACEAFTER = "spaceAfter";
	private static final String KEY_LEFTINDENT = "leftIndent";
	private static final String KEY_RIGHTINDENT = "rightIndent";
	
	public FormatOptionsPage(ZLDialogContent dialogTab) {
		final ZLResource styleResource = ZLResource.resource(KEY_STYLE);

		myComboEntry = new ComboOptionEntry(this, styleResource.getResource(KEY_BASE).getValue());
		myComboEntry.addValue(myComboEntry.initialValue());

		ZLTextStyleCollection collection = ZLTextStyleCollection.getInstance();
		byte styles[] = { REGULAR, TITLE, SECTION_TITLE, SUBTITLE, H1, H2, H3, H4, H5, H6, ANNOTATION, EPIGRAPH, PREFORMATTED, AUTHOR,/* DATEKIND,*/ POEM_TITLE, STANZA, VERSE };
		final int STYLES_NUMBER = styles.length;
		for (int i = 0; i < STYLES_NUMBER; ++i) {
			final ZLTextStyleDecoration decoration = collection.getDecoration(styles[i]);
			if (decoration != null) {
				myComboEntry.addValue(styleResource.getResource(decoration.getName()).getValue());
			}
		}
		dialogTab.addOption("optionsFor", myComboEntry);

		{
			final String name = myComboEntry.initialValue();
			ZLTextBaseStyle baseStyle = collection.baseStyle();

			registerEntries(dialogTab,
				KEY_LINESPACING, new ZLTextLineSpaceOptionEntry(baseStyle.LineSpacePercentOption, dialogTab.getResource(KEY_LINESPACING), false),
				KEY_DUMMY, null,//new ZLSimpleSpinOptionEntry("First Line Indent", baseStyle.firstLineIndentDeltaOption(), -300, 300, 1),
				name
			);

			registerEntries(dialogTab,
				KEY_ALIGNMENT, new ZLTextAlignmentOptionEntry(baseStyle.AlignmentOption, dialogTab.getResource(KEY_ALIGNMENT), false),
				KEY_DUMMY, null,
				name
			);
		}

		for (int i = 0; i < STYLES_NUMBER; ++i) {
			ZLTextStyleDecoration d = collection.getDecoration(styles[i]);
			if ((d != null) && (d.isFullDecoration())) {
				ZLTextFullStyleDecoration decoration = (ZLTextFullStyleDecoration) d;
				final String name = styleResource.getResource(decoration.getName()).getValue();
				
				registerEntries(dialogTab,
					KEY_SPACEBEFORE, new ZLSimpleSpinOptionEntry(decoration.SpaceBeforeOption, 1),
					KEY_LEFTINDENT, new ZLSimpleSpinOptionEntry(decoration.LeftIndentOption, 1),
					name
				);
				
				registerEntries(dialogTab,
					KEY_SPACEAFTER, new ZLSimpleSpinOptionEntry(decoration.SpaceAfterOption, 1),
					KEY_RIGHTINDENT, new ZLSimpleSpinOptionEntry(decoration.RightIndentOption, 1),
					name
				);
				
				registerEntries(dialogTab,
					KEY_LINESPACING, new ZLTextLineSpaceOptionEntry(decoration.LineSpacePercentOption, dialogTab.getResource(KEY_LINESPACING), true),
					KEY_FIRSTLINEINDENT, new ZLSimpleSpinOptionEntry(decoration.FirstLineIndentDeltaOption, 1),
					name
				);

				registerEntries(dialogTab,
					KEY_ALIGNMENT, new ZLTextAlignmentOptionEntry(decoration.AlignmentOption, dialogTab.getResource(KEY_ALIGNMENT), true),
					KEY_DUMMY, null,
					name
				);
			}
		}

		myComboEntry.onValueSelected(0);
	}
}