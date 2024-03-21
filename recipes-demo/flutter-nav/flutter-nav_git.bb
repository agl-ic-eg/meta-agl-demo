SUMMARY = "Flutter Navigation app"
DESCRIPTION = "A Flutter based IVI Dashboard Application for automotive grade Linux."

HOMEPAGE = "https://gerrit.automotivelinux.org/gerrit/apps/flutter-navigation"

BUGTRACKER = "https://github.com/hritik-chouhan/nav-app/issues"

SECTION = "graphics"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://License.md;md5=f712ede8d4f845976061925d1416fc40"


SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/flutter-navigation;protocol=https;branch=${AGL_BRANCH} \
    file://nav_config.yaml \
    "
SRCREV = "53164c0441db946b4293f483a28b524ecdc91e30"
S = "${WORKDIR}/git"

MAPBOX_API_KEY ??= "YOU_NEED_TO_SET_IT_IN_LOCAL_CONF"

inherit agl-app flutter-app

# flutter-app
#############
PUBSPEC_APPNAME = "flutter_navigation"
FLUTTER_BUILD_ARGS = "bundle -v"

# agl-app
#########
AGL_APP_TEMPLATE = "agl-app-flutter"
AGL_APP_ID = "flutter_navigation"
AGL_APP_NAME = "Flutter Navigation"

do_configure:prepend() {
    if [ "${MAPBOX_API_KEY}" = "YOU_NEED_TO_SET_IT_IN_LOCAL_CONF" ]; then
		bbwarn "WARNING: You should set mapbox API key to mapbox_API_KEY variable in local.conf."
	fi
}

do_install:append() {
    install -d ${D}${sysconfdir}/xdg/AGL
    install -m 0644 ${WORKDIR}/nav_config.yaml ${D}${sysconfdir}/xdg/AGL/

    install -m 0755 -d ${D}${sysconfdir}/default/

    echo 'MAPBOX_API_KEY:${MAPBOX_API_KEY}' >> ${D}${sysconfdir}/default/mapboxkey
}

FILES:${PN} += "${sysconfdir}/xdg/AGL ${sysconfdir}/default/"

