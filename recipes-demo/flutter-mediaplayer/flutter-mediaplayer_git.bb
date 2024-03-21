SUMMARY = "Flutter MediaPlayer app for IVI"
DESCRIPTION = "A Flutter based IVI Dashboard Application for automotive grade Linux"

HOMEPAGE = "https://gerrit.automotivelinux.org/gerrit/apps/flutter-mediaplayer"

BUGTRACKER = "https://github.com/hritik-chouhan/musicplayer/issues"

SECTION = "graphics"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://License.md;md5=f712ede8d4f845976061925d1416fc40"


SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/flutter-mediaplayer;protocol=https;branch=${AGL_BRANCH}"
SRCREV = "735de5a38ea937ee13157f6ac594053ddb8b027b"
S = "${WORKDIR}/git"

inherit agl-app flutter-app

# flutter-app
#############
PUBSPEC_APPNAME = "musicplayer"
FLUTTER_BUILD_ARGS = "bundle -v"

# agl-app
#########
AGL_APP_TEMPLATE = "agl-app-flutter"
AGL_APP_ID = "musicplayer"
AGL_APP_NAME = "Flutter MediaPlayer"
