package com.cgcl.cloudesk.screens.act;

import android.content.Context;

public interface ActivityShifter {
		void shift(Context srcActivityContext, Class<?> destActivityCls);
}
