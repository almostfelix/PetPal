package com.petpal.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.petpal.R
import com.petpal.db.Event

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(events: List<Event>) {
    // Correct initialization of currentMonth using mutableStateOf
    val currentMonth = remember { mutableStateOf(YearMonth.now()) }

    // Get the days in the current month and the first day of the week
    val daysInMonth = currentMonth.value.lengthOfMonth()
    val firstDayOfWeek = currentMonth.value.atDay(1).dayOfWeek.value % 7
    val days = (1..daysInMonth).toList()

    val currentDate = LocalDate.now()

    // Example event (you can add logic to fetch events dynamically)
    val event = Event(
        title = "test",
        description = "test",
        date = "2024-11-20",
        type = "test"
    )

    Column(modifier = Modifier.padding(16.dp)) {
        // Month and Year Header
        Box(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
        ) {
            // Row for the month and year text
            Row(
                modifier = Modifier
                    .align(Alignment.CenterStart), // Align the Row to the start
                verticalAlignment = Alignment.CenterVertically // Center content vertically in the Row
            ) {
                Text(
                    text = currentMonth.value.month.getDisplayName(
                        TextStyle.FULL,
                        Locale.getDefault()
                    ) + " " + currentMonth.value.year,
                    modifier = Modifier
                        .height(40.dp)
                        .wrapContentHeight(align = Alignment.CenterVertically)
                        .padding(bottom = 0.dp),
                )
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_forward_ios_32),
                    contentDescription = "Next Month",
                    modifier = Modifier.size(16.dp),
                    tint = colorResource(id = R.color.prim)
                )
            }

            // Row for the icons at the end (Previous & Next Month buttons)
            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd), // Align this Row to the end
                verticalAlignment = Alignment.CenterVertically // Center content vertically in the Row
            ) {
                // Previous Month Icon
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_forward_ios_32),
                    contentDescription = "Previous Month",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            // Update the current month using the mutableStateOf
                            currentMonth.value =
                                currentMonth.value.minusMonths(1) // Decrease month by 1
                        }
                        .graphicsLayer {
                            scaleX = -1f // Flip horizontally
                        },
                    tint = colorResource(id = R.color.prim)
                )

                Spacer(modifier = Modifier.width(8.dp)) // Add spacing between icons if needed

                // Next Month Icon
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_forward_ios_32),
                    contentDescription = "Next Month",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            // Update the current month using the mutableStateOf
                            currentMonth.value =
                                currentMonth.value.plusMonths(1) // Increase month by 1
                        },
                    tint = colorResource(id = R.color.prim)
                )
            }
        }

        // Weekday Headers
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                Text(text = day, color = colorResource(R.color.disabled))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Days of the Month
        LazyVerticalGrid(
            columns = GridCells.Fixed(7), // 7 columns for the days of the week
            modifier = Modifier.fillMaxWidth(),
            content = {
                // Empty cells for days before the first day of the month
                items(firstDayOfWeek) {
                    Box(modifier = Modifier.size(40.dp))
                }

                // Actual days of the month
                items(days) { day ->
                    // Create a LocalDate for the current month and the current day
                    val dayString = LocalDate.of(currentMonth.value.year, currentMonth.value.month, day).toString()

                    // Check if this day has an event
                    val eventForDay = events.find { it.date == dayString }
                    Log.d("CalendarScreen", "Event for day $dayString: $eventForDay")

                    Box(
                        modifier = Modifier
                            .size(40.dp) // Outer box size
                            .clip(CircleShape), // Clip the outer box to a circle
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp) // Outer box size
                                .clip(CircleShape) // Clip the outer box to a circle
                                /*.border(
                                    width = 3.dp,
                                    color = if (currentDate.month.value == currentMonth.value.monthValue && currentDate.dayOfMonth == day){
                                        colorResource(id = R.color.prim) // Highlight current day
                                    } else {
                                        Color.Transparent // Transparent for other days
                                    },
                                    shape = CircleShape
                                )*/
                                .background(
                                    color = when {
                                        eventForDay != null -> colorResource(id = R.color.accent_dark) // Highlight event date
                                        else -> Color.Transparent // Transparent for other days
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = day.toString(),
                                color = when {
                                    currentDate.month.value == currentMonth.value.monthValue && currentDate.dayOfMonth == day -> colorResource(id = R.color.prim) // Highlight event date
                                    else -> colorResource(R.color.black_icon) // Transparent for other days
                                },
                                fontSize = if (currentDate.month == currentMonth.value.month && currentDate.dayOfMonth == day) 18.sp else 16.sp // Larger text for the current day
                            )
                        }
                    }
                }
            }
        )
    }
}

