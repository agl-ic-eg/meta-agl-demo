# Mask out recipes with older versions than those available in
# meta-oe and meta-python
BBMASK += "meta-iot-cloud/recipes-support/cjson/cjson_1.7.10.bb"
BBMASK += "meta-iot-cloud/recipes-devtools/python/python3-argcomplete_1.9.4.bb"
BBMASK += "meta-iot-cloud/recipes-devtools/python/python3-dill_0.2.6.bb"
BBMASK += "meta-iot-cloud/recipes-devtools/python/python3-httplib2_0.11.3.bb"
BBMASK += "meta-iot-cloud/recipes-devtools/python/python3-jmespath_0.9.3.bb"
BBMASK += "meta-iot-cloud/recipes-devtools/python/python3-mypy-extensions_0.4.3.bb"
BBMASK += "meta-iot-cloud/recipes-devtools/python/python3-paramiko_2.4.1.bb"
BBMASK += "meta-iot-cloud/recipes-devtools/python/python3-tabulate_0.8.2.bb"
BBMASK += "meta-iot-cloud/recipes-devtools/python/python3-xmltodict_0.11.0.bb"
