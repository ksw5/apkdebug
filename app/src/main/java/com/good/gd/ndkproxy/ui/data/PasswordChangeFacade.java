package com.good.gd.ndkproxy.ui.data;

import com.good.gd.utils.NoPassChecker;
import java.util.ArrayList;

/* loaded from: classes.dex */
public interface PasswordChangeFacade extends NoPassChecker {
    String getLocaleFormatDate();

    ArrayList<String> setPasswordGuideWithPolicyInformation();
}
