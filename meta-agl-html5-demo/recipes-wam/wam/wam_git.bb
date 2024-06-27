SUMMARY = "WAM"
AUTHOR = "Jani Hautakangas <jani.hautakangas@lge.com>"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "glib-2.0 jsoncpp boost protobuf protobuf-native grpc grpc-native"

SRC_URI = "\
    git://github.com/igalia/${BPN}.git;branch=@58.agl;protocol=https \
    file://WebAppMgrCli \
    file://WebAppMgr.service \
    file://WebAppMgr.env \
    file://WebAppMgr-cef.env \
"

SRCREV = "4fbd6e648913bcf0fba63e4460eb44242c11f71b"

PV = "ose58.agl"

S = "${WORKDIR}/git"

inherit cmake pkgconfig systemd

# Disable some of security flags
# Disable D_FORTIFY_SOURCE=2 and -fstack-protector-strong
# Refer conf/distro/include/security_flags.inc in meta-webos/conf/distro/include/webos.inc
lcl_maybe_fortify = ""
SECURITY_STACK_PROTECTOR = ""

SYSTEMD_SERVICE:${PN} = "WebAppMgr.service"

do_install:append() {
    install -v -d ${D}${sysconfdir}/wam
    install -v -m 644 ${S}/files/launch/security_policy.conf ${D}${sysconfdir}/wam/security_policy.conf
    install -v -D -m 644 ${WORKDIR}/WebAppMgr.service ${D}${systemd_system_unitdir}/WebAppMgr.service
    install -v -D -m 755 ${WORKDIR}/WebAppMgrCli ${D}${bindir}/WebAppMgrCli
}

CXXFLAGS:append:agl-devel = " -DAGL_DEVEL"

do_install:append:agl-devel() {
    # Enable remote inspector and dev mode
    install -d ${D}${localstatedir}/agl-devel/preferences
    touch ${D}${localstatedir}/agl-devel/preferences/debug_system_apps
    touch ${D}${localstatedir}/agl-devel/preferences/devmode_enabled
}

require wam-cef.inc

FILES:${PN} += "${sysconfdir}/init \
                ${sysconfdir}/wam \
                ${bindir} \
                ${libdir}/webappmanager/plugins/*.so"

RDEPENDS:${PN} += " bash grpc-web-proxy"
