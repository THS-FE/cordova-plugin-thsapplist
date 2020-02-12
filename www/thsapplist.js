var exec = require('cordova/exec');

exports.getAppList = function ( success, error) {
    exec(success, error, 'thsapplist', 'getAppList', []);
};
