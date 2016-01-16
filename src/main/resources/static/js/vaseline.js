/**
 * Created by weiliyang on 9/1/15.
 */

function Vaseline() {
    var self = this;

    self.init = function() {
        $(document).ready(function() {
            self.config = {
                bookableModule: $('.m-bookable'),
                bookableItemsWrapper: $('.m-bookable .m-bookable-items-wrapper'),
                bookableStartDatePicker: $('.m-bookable #start-datetime-picker'),
                bookableEndDatePicker: $('.m-bookable #end-datetime-picker'),
                bookableSearchButton: $('.m-bookable #search-bookable')
            };
            self.bindEventHandlers();
            self.loadPageContent();
        });
    };

    self.ajaxCreateBooking = function(bookableId, bookedFor) {
        $.ajax({
            type: 'POST',
            url: '/rest/booking/create/',
            data: {
                bookableId: bookableId,
                bookedFor: bookedFor
            },
            success: function(response) {
                console.log(response);
            }
        });
    };

    self.bindEventHandlers = function() {
        if (self.config.bookableStartDatePicker.length > 0) {
            jQuery(self.config.bookableStartDatePicker).datetimepicker({
                lang: 'zh',
                timepicker: false,
                format: 'Y-m-d'
            });
        }
        if (self.config.bookableEndDatePicker.length > 0) {
            jQuery(self.config.bookableEndDatePicker).datetimepicker({
                lang: 'zh',
                timepicker: false,
                format: 'Y-m-d'
            });
        }

        self.config.bookableSearchButton.on('click', function() {
            self.loadPageContent();
        });

        $('body').on('click', '.m-bookable-item', function(event) {
            event.preventDefault();

            var dis = this;
            var data = [{
                bookableId: $(this).data('item-id'),
                bookedFor: $(this).data('date')
            }];

            $.ajax({
                'headers': {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                'type': 'POST',
                'url': '/rest/booking/create/',
                'data': JSON.stringify({items: data}),
                'dataType': 'json',
                'success': function(response) {
                    if (!$(dis).hasClass('selected')) {
                        $(dis).addClass('selected');
                    }
                }
            });
        }).on('click', '.m-booking-items .m-booking-item .m-booking-cancel', function(event) {
            var src = this;
            event.preventDefault();
            var id = $(this).data('booking-id');
            $.getJSON('/rest/booking/cancel/', {
                bookingId: id
            }, function(response) {
                if (response && response.data && response.data.deleted && response.data.deleted == 1) {
                    var parent = $(src).parent();
                    if (!$(parent).hasClass("selected"))
                        $(parent).addClass("selected");
                }
            });

        }).on('click touchend', '.m-booking-items .m-booking-item .m-booking-name', function(event) {
            $(this).parent().find('.m-booking-cancel').show();
        });
    };

    self.loadPageContent = function() {
        if (self.config.bookableModule.length > 0) {
            $.getJSON('/rest/bookable/list/', {
                ownerId: $('.m-bookable .m-bookable-owner').data('owner-id'),
                dateStart: self.config.bookableStartDatePicker.val(),
                dateEnd: self.config.bookableEndDatePicker.val()
            }, function(response) {
                self.renderBookableResponse(response, self.config.bookableStartDatePicker.val(), self.config.bookableEndDatePicker.val());
            });
        }
    };

    self.__get_date_str = function(date) {
        var str = date.getFullYear();
        var m = date.getMonth() + 1;
        str = str + '-' + (m < 10 ? ('0' + m) : m);
        str = str + '-' + (date.getDate() < 10 ? ('0' + date.getDate()) : date.getDate());
        return str;
    };

    self.renderBookableResponse = function(response, startDate, endDate) {
        self.config.bookableItemsWrapper.empty();
        var start = new Date(startDate);
        var end = new Date(endDate);
        for (var i = start; i <= end; i = new Date(i.getTime() + 24 * 3600 * 1000)) {
            self.renderBookablesPerDate(response.bookables, response.booked, self.__get_date_str(i));
        }
    }

    self.renderBookablesPerDate = function(bookables, booked, date) {
        var bookedNames = [];
        if (booked[date]) {
            $.each(booked[date], function (k, v) {
                bookedNames.push(v.name);
            });
        }

        var itemContainer = $('<div class="m-bookable-date"></div>');
        itemContainer.append($('<div class="m-date"><span>' + date + '</span></div>'));
        var ul = $('<ul></ul>');
        itemContainer.append(ul);
        itemContainer.append($('<div class="clearfix"></div>'))
        $.each(bookables, function(k, bookable) {
           if ($.inArray(bookable.name, bookedNames) == -1) {
               var item = $('<a href="#" class="m-bookable-item">' + bookable.name + '</a>');
               $(item).attr('data-item-id', bookable.id);
               $(item).attr('data-date', date);
               var li = $('<li></li>');
               li.append(item);
               ul.append(li);
           }
        });

        self.config.bookableItemsWrapper.append(itemContainer);
    }
}
