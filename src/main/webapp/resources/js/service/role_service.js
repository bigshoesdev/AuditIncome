'use strict';

App.factory('User', ['$resource', function ($resource) {
        //$resource() function returns an object of resource class
        return $resource(
                '/sts/UserController/user/:id',
                {id: '@id'}, //Handy for update & delete. id will be set with id of instance
        {
            update: {
                method: 'PUT' // To send the HTTP Put request when calling this custom update method.
            }

        }
        );
    }]);