FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

# Remove curl dependency to disable WebDAV support.
# Ideally this should be addressed by adding a PACKAGECONFIG option
# in the upstream recipe.
DEPENDS:remove = "curl"

SRC_URI += "file://0001-output-PipeWire-activate-stream-in-Drain.patch \
            file://0002-output-PipeWire-skip-Cancel-if-already-drained.patch \
            file://0003-output-PipeWire-call-pw_stream_flush-in-Cancel.patch \
            file://0004-output-PipeWire-after-Cancel-refill-buffer-before-re.patch \
            file://mpd-volatiles.conf \
"

# The older meson in poky dunfell complains about the use of the
# build.{c_std,cpp_std} options even though they work, so we need to
# flag them to be ignored in the unknown configure option QA test.
# This can be removed after upgrading to Yocto Project 3.5.
UNKNOWN_CONFIGURE_OPT_IGNORE = "build.c_std build.cpp_std"

# This also needs to be pushed upstream.
PACKAGECONFIG[pipewire] = "-Dpipewire=enabled,-Dpipewire=disabled,pipewire"

# Prune the default configuration a bit to remove various streaming/server
# options that are not required.
PACKAGECONFIG = " \
    ${@bb.utils.contains("LICENSE_FLAGS_ACCEPTED", "commercial", "aac", "", d)} \
    bzip2 \
    daemon \
    ${@bb.utils.contains("LICENSE_FLAGS_ACCEPTED", "commercial", "ffmpeg aac", "", d)} \
    flac \
    libsamplerate \
    mpg123 \
    sndfile \
    opus \
    vorbis \
    wavpack \
    zlib \
    pipewire \
    id3tag \
"

SYSTEMD_AUTO_ENABLE = "enable"

# Override default as AGL's PipeWire configuration does not make use of audio
# group.  As opposed to using the group setting in mpd.conf, this keeps the
# files created in /var/lib/mpd as mpd.mpd, which seems safer.
USERADD_PARAM:${PN} = " \
    --system --no-create-home \
    --home ${localstatedir}/lib/mpd \
    --groups pipewire \
    --user-group mpd"

# Install volatile redirect for /var/lib/mpd to avoid any chance of metadata
# corruption on reboot.  This matches what as previously done with the
# lightmediascanner configuration.
do_install:append() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
	install -D -m 0644 ${WORKDIR}/mpd-volatiles.conf ${D}${sysconfdir}/tmpfiles.d/mpd.conf
    fi
}
