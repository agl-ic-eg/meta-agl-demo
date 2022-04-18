SUMMARY = "WAM"
AUTHOR = "Jani Hautakangas <jani.hautakangas@lge.com>"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit cmake

DEPENDS = "glib-2.0 jsoncpp boost chromium protobuf protobuf-native libxml2"

EXTRA_OECMAKE = "\
    -DCMAKE_BUILD_TYPE=Release \
    -DCMAKE_INSTALL_PREFIX=${prefix} \
    -DPLATFORM_NAME=${@'${DISTRO}'.upper().replace('-', '_')} \
    -DCHROMIUM_SRC_DIR=${STAGING_INCDIR}/chromium"

PR="r0"

PROVIDES += "virtual/webruntime"
RPROVIDES:${PN} += "virtual/webruntime"

# Disable some of securit_flags
# Disable D_FORTIFY_SOURCE=2 and -fstack-protector-strong
# Refer conf/distro/include/security_flags.inc in meta-webos/conf/distro/include/webos.inc
lcl_maybe_fortify = ""
SECURITY_STACK_PROTECTOR = ""

SRC_URI = "\
    git://github.com/igalia/${BPN}.git;branch=@55.agl.marlin;protocol=https \
    file://WebAppMgr.service \
    file://WebAppMgr.env \
"
S = "${WORKDIR}/git"
SRCREV = "f4c2e95afb0ed0cd22095d567fa437a6ccb8c5ab"

PV = "ose55.agl.marlin"

do_install:append() {
    install -v -d ${D}${sysconfdir}/wam
    install -v -m 644 ${S}/files/launch/security_policy.conf ${D}${sysconfdir}/wam/security_policy.conf
    install -v -d ${D}${systemd_user_unitdir}
    install -v -m 644 ${WORKDIR}/WebAppMgr.service ${D}${systemd_user_unitdir}/WebAppMgr.service
    install -v -d ${D}${sysconfdir}/default/
    install -v -m 644 ${WORKDIR}/WebAppMgr.env ${D}${sysconfdir}/default/WebAppMgr.env
    ln -snf WebAppMgr ${D}${bindir}/web-runtime
    install -v -d ${D}${systemd_user_unitdir}/agl-session.target.wants
    ln -sf ../WebAppMgr.service ${D}${systemd_user_unitdir}/agl-session.target.wants/
}

FILES:${PN} += "${sysconfdir}/init ${sysconfdir}/wam ${libdir}/webappmanager/plugins/*.so ${systemd_user_unitdir}"

CXXFLAGS:append:agl-devel = " -DAGL_DEVEL"

do_install:append:agl-devel() {
    # Enable remote inspector and dev mode
    install -d ${D}${localstatedir}/agl-devel/preferences
    touch ${D}${localstatedir}/agl-devel/preferences/debug_system_apps
    touch ${D}${localstatedir}/agl-devel/preferences/devmode_enabled
}
