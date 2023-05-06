FILESEXTRAPATHS:prepend := "${THISDIR}/agl-container-guest:"

# Allow input device for agl-driver
GROUPADD_PARAM:${PN} = "\
	--system display ; \
	--system video ; \
	--system input ; \
	--system pipewire ; \
	-g 1001 agl-driver ; \
	-g 1003 applaunchd ; \
"
USERADD_PARAM:${PN} = "\
  -g 1001 -u 1001 -G video,input,display,pipewire,applaunchd -o -d /home/agl-driver -m -K PASS_MAX_DAYS=-1 agl-driver ; \
  -g 1003 -u 1003 -o -d / -K PASS_MAX_DAYS=-1 applaunchd ; \
"
