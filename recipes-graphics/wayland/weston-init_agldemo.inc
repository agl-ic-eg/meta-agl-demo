FILESEXTRAPATHS:prepend := "${THISDIR}/weston-init:"

inherit agl-graphical

AGL_DEFAULT_WESTONSTART ??= "/usr/bin/agl-compositor --config ${sysconfdir}/xdg/weston/weston.ini"
WESTON_USE_PIXMAN ??= "0"

WESTONSTART ??= "${AGL_DEFAULT_WESTONSTART} ${WESTONARGS}"
WESTONSTART:append = " ${@bb.utils.contains("IMAGE_FEATURES", "debug-tweaks", " --log=${DISPLAY_XDG_RUNTIME_DIR}/compositor.log", "",d)}"
WESTONSTART:append = " ${@bb.utils.contains("DISTRO_FEATURES", "agl-devel", " --debug", "",d)}"
WESTONSTART:append = " ${@bb.utils.contains("WESTON_USE_PIXMAN", "1", " --use-pixman", "",d)}"

# Systemd name of DRM device to have weston/agl-compositor startup depend
# upon, if required.  Currently only x86-64 seems to need a dependency to
# avoid failures due to racing with i915 driver init on e.g. UpSquared.
# It seems safer for now to only apply it there rather than doing a blanket
# default everywhere that might then need to be over-ridden for vendor BSPs.
WESTON_DRM_DEVICE ?= ""

WIFILES = " \
    file://weston.conf.in \
    file://weston-dep.conf.in \
    file://tmpfiles.conf.in \
    file://zz-dri.rules.in \
    file://zz-input.rules.in \
    file://zz-tty.rules.in \
    file://zz-video.rules.in \
"

WIFILES:append:imx = " \
    file://zz-dri-imx.rules.in \
"

SRC_URI:append = " ${WIFILES}"

do_install:append() {
    # Remove upstream weston udev rules just to be safe
    rm -f ${D}${sysconfdir}/udev/rules.d/71-weston-drm.rules

    # Process ".in" files
    files=$(echo ${WIFILES} | sed s,file://,,g)
    for f in ${files}; do
        g=${f%.in}
        if [ "${f}" != "${g}" ]; then
            sed -e "s,@WESTONUSER@,${WESTONUSER},g" \
                -e "s,@WESTONGROUP@,${WESTONGROUP},g" \
                -e "s,@XDG_RUNTIME_DIR@,${DISPLAY_XDG_RUNTIME_DIR},g" \
                -e "s,@WESTONSTART@,${WESTONSTART},g" \
                -e "s,@WESTON_DRM_DEVICE@,${WESTON_DRM_DEVICE},g" \
                    ${WORKDIR}/${f} > ${WORKDIR}/${g}
        fi
    done

    # Install weston drop-in
    install -d ${D}${systemd_system_unitdir}/weston@.service.d
    install -m644 ${WORKDIR}/weston.conf ${D}/${systemd_system_unitdir}/weston@.service.d/weston-init.conf

    # Install weston DRM device dependency drop-in if required
    if [ -n "${WESTON_DRM_DEVICE}" ]; then
        install -m 0644 ${WORKDIR}/weston-dep.conf ${D}/${systemd_system_unitdir}/weston@.service.d/
    fi

    # Install tmpfiles drop-in
    install -d ${D}${libdir}/tmpfiles.d
    install -m644 ${WORKDIR}/tmpfiles.conf ${D}${libdir}/tmpfiles.d/weston-init.conf

    # Install udev rules
    install -d ${D}${sysconfdir}/udev/rules.d
    for f in ${files}; do
        g=${f%.in}
        h=${g%.rules}
        if [ "${g}" != "${h}" ]; then
            install -m644 ${WORKDIR}/${g} ${D}${sysconfdir}/udev/rules.d
        fi
    done
}

FILES:${PN} += " \
    ${libdir}/tmpfiles.d/ \
    ${systemd_system_unitdir}/weston@.service.d/ \
"
