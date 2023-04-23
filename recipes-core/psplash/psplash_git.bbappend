require ${@bb.utils.contains('AGL_FEATURES', 'agldemo', 'psplash_agldemo.inc', '', d)}
require ${@bb.utils.contains('AGL_FEATURES', 'agl-container-guest-demo', 'psplash_agl-container-guest-demo.inc', '', d)}
