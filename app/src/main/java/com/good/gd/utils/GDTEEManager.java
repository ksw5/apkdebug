package com.good.gd.utils;

/* loaded from: classes.dex */
public class GDTEEManager implements GDTEEControlInterface {
    private static GDTEEManager _instance;
    private GDTEEControlInterface mImpl = new GDTEEManagerImpl();

    private GDTEEManager() {
    }

    public static GDTEEManager createInstance() {
        if (_instance == null) {
            _instance = new GDTEEManager();
        }
        return _instance;
    }

    public static GDTEEManager getInstance() {
        return _instance;
    }

    @Override // com.good.gd.utils.GDTEEControlInterface
    public void Init() {
        this.mImpl.Init();
    }
}
