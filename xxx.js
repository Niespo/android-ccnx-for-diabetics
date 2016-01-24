    module.factory('ModalService', ['$animate', '$document', '$compile', '$controller', '$http', '$rootScope', '$q', '$templateRequest', '$timeout',
        function ($animate, $document, $compile, $controller, $http, $rootScope, $q, $templateRequest, $timeout) {
            var ON_CLOSE_DELAY = 0;

            return {
                showModal: showModal
            };

            function showModal(parameters) {
                var controller = parameters.controller;
                if (!controller) {
                    return $q.reject("You must specify controller");
                }

                return applyTemplate(parameters.template, parameters.templateUrl)
                    .then(createModal, handleError);

                function createModal(template) {
                    var modalScope = $rootScope.$new(),
                        onClosePromise = $q.defer(),
                        inputs = {
                            $scope: modalScope,
                            close: close
                        };

                    if (parameters.inputs) {
                        angular.extend(inputs, parameters.inputs);
                    }

                    var modalElement = compileModal(template, modalScope);
                    inputs.$element = modalElement;
                    var modalController = $controller(parameters.controller, inputs);

                    if (parameters.controllerAs) {
                        modalScope[parameters.controllerAs] = modalController;
                    }

                    appendToDOM(parameters.appendElement, modalElement);

                    return {
                        controller: modalController,
                        scope: modalScope,
                        element: modalElement,
                        close: onClosePromise.promise
                    };

                    function close(result, delay) {
                        delay = delay || ON_CLOSE_DELAY;
                        $timeout(closeModal, delay);

                        function closeModal() {
                            onClosePromise.resolve(result);
                            $animate.leave(modalElement)
                                .then(onLeftAnimation);
                        }

                        function onLeftAnimation() {
                            modalScope.$destroy();
                        }
                    }

                    function compileModal(template, scope) {
                        return $compile(template)(scope);
                    }

                    function appendToDOM(appendedElement, modalElement) {
                        if (appendedElement) {
                            return appendChild(appendedElement, modalElement);
                        }
                        var body = $document.find('body');
                        return appendChild(body, modalElement);
                    }
                }

                function handleError(error) {
                    return $q.reject(error);
                }

                function applyTemplate(template, templateUrl) {
                    if (templateUrl) {
                        return $templateRequest(templateUrl, true);
                    }
                    if (template) {
                        return $q.resolve(template);
                    }
                    return $q.reject("You should specify template or templateUrl");
                }

                function appendChild(parent, child) {
                    var children = parent.children();
                    if (!children.length) {
                        return $animate.enter(child, parent);
                    }

                    return $animate.enter(child, parent, children[children.length - 1]);
                }
            }

        }]);
